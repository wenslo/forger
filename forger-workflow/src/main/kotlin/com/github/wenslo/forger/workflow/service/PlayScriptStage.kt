package com.github.wenslo.forger.workflow.service

import com.github.wenslo.forger.workflow.entity.PlayScript
import com.github.wenslo.forger.workflow.entity.PlayScriptExecuteRecord

/**
 * @author wenhailin
 * @date 2022/10/26 20:33
 */
interface PlayScriptStage {
    fun paramValid(playScript: PlayScript)

    fun run(playScript: PlayScript)

    fun execute(record: PlayScriptExecuteRecord)

    fun getExecuteResult(recordLogId: Int)

    fun callback()

    fun downloadFileByLogId()

}