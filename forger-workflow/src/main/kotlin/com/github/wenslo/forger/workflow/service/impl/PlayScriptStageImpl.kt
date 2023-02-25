package com.github.wenslo.forger.workflow.service.impl

import com.github.wenslo.forger.core.exceptions.BusinessException
import com.github.wenslo.forger.core.inline.getLogger
import com.github.wenslo.forger.workflow.cache.ExecuteFactory
import com.github.wenslo.forger.workflow.domain.ExecuteShip
import com.github.wenslo.forger.workflow.domain.ExecutorResponse
import com.github.wenslo.forger.workflow.entity.PlayScript
import com.github.wenslo.forger.workflow.entity.PlayScriptAction
import com.github.wenslo.forger.workflow.entity.PlayScriptExecuteRecordLog
import com.github.wenslo.forger.workflow.enums.ExecuteStatus
import com.github.wenslo.forger.workflow.enums.ExecutorType
import com.github.wenslo.forger.workflow.enums.IsFlag
import com.github.wenslo.forger.workflow.enums.PlayScriptProcessStatus
import com.github.wenslo.forger.workflow.repository.PlayScriptActionRepository
import com.github.wenslo.forger.workflow.repository.PlayScriptExecuteRecordLogRepository
import com.github.wenslo.forger.workflow.service.ActionProducerService
import com.github.wenslo.forger.workflow.service.PlayScriptExecuteRecordService
import com.github.wenslo.forger.workflow.service.PlayScriptService
import com.github.wenslo.forger.workflow.service.PlayScriptStage
import com.github.wenslo.forger.workflow.utils.FieldDtoUtil
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.concurrent.locks.ReentrantLock

/**
 * @author wenhailin
 * @date 2022/10/29 23:45
 */
@Service
class PlayScriptStageImpl : PlayScriptStage {
    val logger = getLogger<PlayScriptStageImpl>()

    companion object {
        val lock = ReentrantLock()
    }

    @Autowired
    lateinit var gson: Gson

    @Autowired
    lateinit var recordService: PlayScriptExecuteRecordService

    @Autowired
    lateinit var executeFactory: ExecuteFactory

    @Autowired
    lateinit var actionProducerService: ActionProducerService

    @Autowired
    lateinit var actionRepository: PlayScriptActionRepository

    @Autowired
    lateinit var playScriptService: PlayScriptService

    @Autowired
    lateinit var recordLogRepository: PlayScriptExecuteRecordLogRepository

    @Suppress("UNCHECKED_CAST")
    override fun paramValid(playScript: PlayScript) {
        for (fieldDtoList in playScript.params.values) {
            FieldDtoUtil.valid(fieldDtoList)
        }
    }

    override fun run(playScript: PlayScript) {
        logger.info("PlayScript is running...")
        logger.info("PlayScript info is {}", gson.toJson(playScript))
        //save play script
        playScriptService.savePlayScript(playScript)
        //save play script line
        playScriptService.savePlayScriptNodeLine(playScript)
        //save play script node and actions
        playScriptService.savePlayScriptNode(playScript)
        //save action params
        playScriptService.savePlayScriptParams(playScript)
        //save action param shuttles
        playScriptService.saveParamShuttles(playScript)
        //save template params
        playScriptService.saveTemplateParams(playScript)
        //save play script record
        val ships = recordService.saveRecordAndGenerateShip(playScript)
        //play scripts may different types
        //sending ships to executing
        ships.forEach {
            actionProducerService.sendNow(it)
        }
    }

    override fun execute(ship: ExecuteShip) {
        logger.info("Execute starting... ship info is ${gson.toJson(ship)}")
        this.invokeExecutor(ship)
    }

    private fun invokeExecutor(ship: ExecuteShip) {
        val current = ship.current
        if (current.isBlank()) {
            logger.info("It is empty at current record")
            return
        }
        val action = actionRepository.findTopByPlayScriptIdAndUniqueId(ship.playScriptId, current)
        action?.let {
            executeFactory.getExecutor(it.executorType)?.let { executor ->
                logger.info("Executor is ：{}", gson.toJson(executor.getResourceInfo()))
                //record log generate
                val recordLog = generateRecordLog(ship, it.executorType)
                ship.recordLogId = recordLog.id ?: 0
                val executeResponse = executor.execute(ship)
                //populate record log information
                recordLog.apply {
                    status = executeResponse.status
                    message = executeResponse.message
                    hasReportFile = executeResponse.hasReportFile
                    link = executeResponse.link
                    if (status == ExecuteStatus.SUCCEED || status == ExecuteStatus.ERROR) {
                        endTime = LocalDateTime.now()
                        finishFlag = IsFlag.YES
                    }
                }
                //engine flow processing
                //lock it
                lock.lock()
                this.finishedHook(executeResponse, action, recordLog)
                lock.unlock()
            }
        }


    }

    /**
     * finished hook
     */
    private fun finishedHook(
        executeResponse: ExecutorResponse,
        action: PlayScriptAction,
        recordLog: PlayScriptExecuteRecordLog
    ) {
        // previous actions check, status must be success, unless next action is threshold
        // put action into redis, and redis go ahead sync play script state
        when (executeResponse.status) {
            ExecuteStatus.NONE -> {
                throw BusinessException("Executed response has wrong status")
            }

            ExecuteStatus.SUCCEED -> {
                this.actionSucceedHandler(action, recordLog)
            }

            ExecuteStatus.ERROR -> {
                this.actionErrorHandler(executeResponse, recordLog)
            }

            ExecuteStatus.WAITING -> {
                this.actionHasCallbackHandler(executeResponse, recordLog)
            }

            ExecuteStatus.PARAMS_NOT_EXISTS -> {
                this.actionParamsNotExistsHandler(executeResponse, recordLog)
            }

            ExecuteStatus.THRESHOLD_NOT_PASS -> {
                this.actionThresholdNotPassHandler(executeResponse, recordLog)
            }
        }
        recordLogRepository.save(recordLog)
    }

    private fun actionThresholdNotPassHandler(
        executeResponse: ExecutorResponse,
        recordLog: PlayScriptExecuteRecordLog
    ) {
        recordLog.status = executeResponse.status
        recordLog.message = "Action isn't pass by threshold"

    }

    private fun actionParamsNotExistsHandler(executeResponse: ExecutorResponse, recordLog: PlayScriptExecuteRecordLog) {
        recordLog.status = executeResponse.status
        recordLog.message = "Action parameters isn't exists"
    }

    private fun actionHasCallbackHandler(executeResponse: ExecutorResponse, recordLog: PlayScriptExecuteRecordLog) {
        recordLog.status = executeResponse.status
        recordLog.message = "Action hasn't callback, waiting..."
        //TODO put mq to waiting
    }

    private fun actionErrorHandler(
        executeResponse: ExecutorResponse,
        recordLog: PlayScriptExecuteRecordLog
    ) {
        recordLog.status = executeResponse.status
        recordLog.message = executeResponse.message
    }

    private fun actionSucceedHandler(
        action: PlayScriptAction,
        recordLog: PlayScriptExecuteRecordLog
    ) {
        //find next action by current action, if these dependence are not all pass, waiting for another handler to finished it
        val allMap = playScriptService.actionMapByUniqueId(action.playScriptId)
        if (allMap.isEmpty()) {
            throw BusinessException("Engine has error")
        }
        allMap[action.uniqueId]?.successFlag = IsFlag.YES
        //TODO async

        val next = action.next
        if (next.isEmpty()) {
            //check all finished
            val previous = playScriptService.findNextEmpty(action.playScriptId)
            val allFinished = previous.all { (allMap[it]?.successFlag ?: IsFlag.NO) == IsFlag.YES }
            if (allFinished) this.playScriptIsOver(recordLog)
        } else {
            //TODO
            val previousMap = playScriptService.actionPreviousMap(action.playScriptId)
            for (actionUniqueId in next) {
                val previous = previousMap[actionUniqueId]
                if (previous?.isNotEmpty() == true) {
                    val previousCheckPass = previous.all { allMap[it]?.successFlag == IsFlag.YES }
                    if (previousCheckPass) flowToNext(actionUniqueId, recordLog)
                } else {
                    logger.error("The action hasn't previous action but at processing flow, error data!")
                }
            }
        }
    }

    private fun flowToNext(nextAction: String, recordLog: PlayScriptExecuteRecordLog) {
        val ship = ExecuteShip().apply {
            this.playScriptId = recordLog.playScriptId
            this.playScriptUniqueId = recordLog.playScriptUniqueId
            this.playScriptRecordId = recordLog.recordId
            this.current = nextAction
        }
        logger.info("flow to next action, information is ${gson.toJson(ship)}")
        actionProducerService.sendNow(ship)
    }

    private fun playScriptIsOver(recordLog: PlayScriptExecuteRecordLog) {
        // save invoked info
        recordService.finishById(PlayScriptProcessStatus.SUCCEED, recordLog.recordId)

    }

    private fun generateRecordLog(ship: ExecuteShip, executorType: ExecutorType): PlayScriptExecuteRecordLog {
        val recordLog = PlayScriptExecuteRecordLog(
            playScriptId = ship.playScriptId,
            playScriptUniqueId = ship.playScriptUniqueId,
            recordId = ship.playScriptRecordId,
            actionUniqueId = ship.current,
            executorType = executorType,
            beginTime = LocalDateTime.now(),
            executeFlag = IsFlag.YES
        ).also {
            recordLogRepository.save(it)
        }
        return recordLog
    }

    override fun getExecuteResult(recordLogId: Int) {
        TODO("Not yet implemented")
    }

    override fun callback() {
        TODO("Not yet implemented")
    }

    override fun downloadFileByLogId() {
        TODO("Not yet implemented")
    }
}