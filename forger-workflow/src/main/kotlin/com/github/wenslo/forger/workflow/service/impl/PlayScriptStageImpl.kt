package com.github.wenslo.forger.workflow.service.impl

import com.github.wenslo.forger.core.inline.getLogger
import com.github.wenslo.forger.workflow.cache.ExecuteFactory
import com.github.wenslo.forger.workflow.entity.PlayScript
import com.github.wenslo.forger.workflow.entity.PlayScriptAction
import com.github.wenslo.forger.workflow.entity.PlayScriptExecuteRecord
import com.github.wenslo.forger.workflow.repository.PlayScriptExecuteRecordRepository
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
    lateinit var recordRepository: PlayScriptExecuteRecordRepository

    @Autowired
    lateinit var executeFactory: ExecuteFactory


    override fun paramValid(playScript: PlayScript) {
        TODO("Not yet implemented")
    }

    override fun run(playScript: PlayScript) {
        logger.info("PlayScript is running...")
        logger.info("PlayScript info is {}", gson.toJson(playScript))
        //TODO save play script
        // save play script record
        val playScriptExecuteRecord = PlayScriptExecuteRecord()
    }

    override fun execute(record: PlayScriptExecuteRecord) {
        logger.info("Execute starting... record info is ${gson.toJson(record)}")
        this.invokeExecutor(record)
    }

    private fun invokeExecutor(record: PlayScriptExecuteRecord) {
        val current = record.current
        if (current.isEmpty()) {
            logger.info("It is empty at current record")
            return
        }
        //TODO actions
        val actions = listOf<PlayScriptAction>()
        val failureActions = mutableListOf<String>()
        var hasFailed = false
        for (action in actions) {
            val executorId = action.executorId
            val executor = executeFactory.getExecutor(executorId) ?: continue
            logger.info("Executor is ：{}", gson.toJson(executor.getResourceInfo()))
            val executeResponse = executor.execute(Any())
            if (executeResponse.code != 0) {
                failureActions.add(action.uniqueId)
                hasFailed = true
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