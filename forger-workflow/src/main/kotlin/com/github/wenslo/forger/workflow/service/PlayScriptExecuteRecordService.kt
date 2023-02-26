package com.github.wenslo.forger.workflow.service

import com.github.wenslo.forger.data.jpa.service.LongIdService
import com.github.wenslo.forger.workflow.condition.PlayScriptExecuteRecordCondition
import com.github.wenslo.forger.workflow.domain.ExecuteShip
import com.github.wenslo.forger.workflow.entity.jpa.PlayScript
import com.github.wenslo.forger.workflow.entity.jpa.PlayScriptExecuteRecord
import com.github.wenslo.forger.workflow.enums.IsFlag
import com.github.wenslo.forger.workflow.enums.PlayScriptProcessStatus

/**
 * @author wenhailin
 * @date 2022/11/4 15:36
 */
interface PlayScriptExecuteRecordService : LongIdService<PlayScriptExecuteRecord, PlayScriptExecuteRecordCondition> {
    /**
     * search first node to execute, save record and generate ExecuteShip
     */
    fun saveRecordAndGenerateShip(playScript: PlayScript): List<ExecuteShip>

    /**
     * finish
     */
    fun finishById(succeed: PlayScriptProcessStatus, recordId: Long)

    fun invokedStatusMapByPlayScriptId(playScriptId: Long): Map<String, IsFlag?>
}