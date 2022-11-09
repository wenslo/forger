package com.github.wenslo.forger.workflow.service.impl

import com.github.wenslo.forger.core.inline.getLogger
import com.github.wenslo.forger.workflow.cache.ExecuteFactory
import com.github.wenslo.forger.workflow.domain.ExecuteShip
import com.github.wenslo.forger.workflow.entity.PlayScript
import com.github.wenslo.forger.workflow.entity.PlayScriptExecuteRecordLog
import com.github.wenslo.forger.workflow.enums.IsFlag
import com.github.wenslo.forger.workflow.repository.PlayScriptActionRepository
import com.github.wenslo.forger.workflow.repository.PlayScriptExecuteRecordLogRepository
import com.github.wenslo.forger.workflow.service.ActionProducerService
import com.github.wenslo.forger.workflow.service.PlayScriptExecuteRecordService
import com.github.wenslo.forger.workflow.service.PlayScriptService
import com.github.wenslo.forger.workflow.service.PlayScriptStage
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

/**
 * @author wenhailin
 * @date 2022/10/29 23:45
 */
@Service
class PlayScriptStageImpl : PlayScriptStage {
    val logger = getLogger<PlayScriptStageImpl>()

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


    override fun paramValid(playScript: PlayScript) {
        TODO("Not yet implemented")
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
            executeFactory.getExecutor(it.executorId)?.let { executor ->
                logger.info("Executor is ：{}", gson.toJson(executor.getResourceInfo()))
                //record log generate
                val recordLog = generateRecordLog(ship, it.executorId)
                val executeResponse = executor.execute(Any())
                //populate record log information
                recordLog.apply {
                    status = executeResponse.status
                    message = executeResponse.message
                    hasReportFile = executeResponse.hasReportFile
                    link = executeResponse.link
                }.also { log ->
                    recordLogRepository.save(log)
                }
                //TODO engine flow processing
            }
        }


    }

    private fun generateRecordLog(ship: ExecuteShip, executorId: String): PlayScriptExecuteRecordLog {
        val recordLog = PlayScriptExecuteRecordLog(
            playScriptId = ship.playScriptId,
            playScriptUniqueId = ship.playScriptUniqueId,
            recordId = ship.playScriptRecordId,
            actionUniqueId = ship.current,
            executorId = executorId,
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