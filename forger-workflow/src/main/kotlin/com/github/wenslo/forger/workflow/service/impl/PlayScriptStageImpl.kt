package com.github.wenslo.forger.workflow.service.impl

import com.github.wenslo.forger.core.inline.getLogger
import com.github.wenslo.forger.workflow.cache.ExecuteFactory
import com.github.wenslo.forger.workflow.domain.ExecuteShip
import com.github.wenslo.forger.workflow.entity.PlayScript
import com.github.wenslo.forger.workflow.repository.PlayScriptActionRepository
import com.github.wenslo.forger.workflow.service.ActionProducerService
import com.github.wenslo.forger.workflow.service.PlayScriptExecuteRecordService
import com.github.wenslo.forger.workflow.service.PlayScriptStage
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

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


    override fun paramValid(playScript: PlayScript) {
        TODO("Not yet implemented")
    }

    override fun run(playScript: PlayScript) {
        logger.info("PlayScript is running...")
        logger.info("PlayScript info is {}", gson.toJson(playScript))
        //TODO save play script
        //TODO save play script line
        //TODO save play script node
        //TODO save play script actions
        //TODO save play script record

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
                val executeResponse = executor.execute(Any())
                //TODO handler at there
            }
        }


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