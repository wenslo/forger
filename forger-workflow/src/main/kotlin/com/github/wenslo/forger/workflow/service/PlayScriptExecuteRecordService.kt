package com.github.wenslo.forger.workflow.service

import com.github.wenslo.forger.workflow.domain.ExecuteShip
import com.github.wenslo.forger.workflow.entity.PlayScript

/**
 * @author wenhailin
 * @date 2022/11/4 15:36
 */
interface PlayScriptExecuteRecordService {

    fun saveRecordAndGenerateShip(playScript: PlayScript): List<ExecuteShip>
}