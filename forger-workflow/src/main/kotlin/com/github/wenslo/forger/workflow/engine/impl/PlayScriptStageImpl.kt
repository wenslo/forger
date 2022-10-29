package com.github.wenslo.forger.workflow.engine.impl

import com.github.wenslo.forger.core.inline.getLogger
import com.github.wenslo.forger.workflow.engine.PlayScriptStage
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


    override fun executeBefore() {
        TODO("Not yet implemented")
    }

    override fun executeAgain() {
        TODO("Not yet implemented")
    }

    override fun execute() {
        TODO("Not yet implemented")
    }

    override fun getExecuteResult() {
        TODO("Not yet implemented")
    }

    override fun callback() {
        TODO("Not yet implemented")
    }

    override fun downloadFileByLogId() {
        TODO("Not yet implemented")
    }
}