package com.github.wenslo.forger.workflow.service.impl

import com.github.wenslo.forger.core.inline.getLogger
import com.github.wenslo.forger.workflow.cache.ExecuteFactory
import com.github.wenslo.forger.workflow.cache.ExecuteFactory.Companion.EXECUTOR_MAP
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


    override fun executeBefore() {
        TODO("Not yet implemented")
    }

    override fun executeAgain() {
        TODO("Not yet implemented")
    }

    override fun execute(record: PlayScriptExecuteRecord) {
        logger.info("Execute starting... record info is ${gson.toJson(record)}")
        this.invokeExecutor(record)
    }

    private fun invokeExecutor(record: PlayScriptExecuteRecord) {
        for (executorId in EXECUTOR_MAP.keys) {
            val executor = executeFactory.getExecutor(executorId) ?: continue
            logger.info("Executor is ：{}", gson.toJson(executor.getResourceInfo()))
            logger.info("Executed result is ：{}", executor.execute(Any()))
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