package com.github.wenslo.forger.workflow.service

import com.github.wenslo.forger.workflow.entity.PlayScriptExecuteRecord

/**
 * @author wenhailin
 * @date 2022/10/26 20:33
 */
interface PlayScriptStage {
    fun paramValid()

    fun executeAgain()

    fun execute(record: PlayScriptExecuteRecord)

    fun getExecuteResult(recordLogId: Int)

    fun callback()

    fun downloadFileByLogId()

}