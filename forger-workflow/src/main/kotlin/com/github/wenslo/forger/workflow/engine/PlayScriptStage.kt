package com.github.wenslo.forger.workflow.engine

/**
 * @author wenhailin
 * @date 2022/10/26 20:33
 */
interface PlayScriptStage {
    fun executeBefore()

    fun executeAgain()

    fun execute()

    fun getExecuteResult()

    fun callback()

    fun downloadFileByLogId()

}