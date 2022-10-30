package com.github.wenslo.forger.workflow.engine

import com.github.wenslo.forger.workflow.entity.PlayScriptExecuteRecord

/**
 * @author wenhailin
 * @date 2022/10/26 20:33
 */
interface PlayScriptStage {
    fun executeBefore()

    fun executeAgain()

    fun execute(record: PlayScriptExecuteRecord)

    fun getExecuteResult(recordLogId: Int)

    fun callback()

    fun downloadFileByLogId()

}