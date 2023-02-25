package com.github.wenslo.forger.workflow.service

import com.github.wenslo.forger.workflow.domain.ExecuteShip
import com.github.wenslo.forger.workflow.entity.jpa.PlayScript

/**
 * @author wenhailin
 * @date 2022/10/26 20:33
 */
interface PlayScriptStage {
    fun paramValid(playScript: PlayScript)

    fun run(playScript: PlayScript)

    fun execute(ship: ExecuteShip)

    fun getExecuteResult(recordLogId: Int)

    fun callback()

    fun downloadFileByLogId()

}