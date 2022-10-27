package com.github.wenslo.forger.workflow.entity

import com.github.wenslo.forger.mp.model.LongIdEntity
import com.github.wenslo.forger.workflow.enums.PlayScriptProcessStatus

/**
 * @author wenhailin
 * @date 2022/10/27 10:44
 */
data class PlayScriptExecuteRecord(
    var playScriptId: Int = 0,
    var playScriptUniqueId: String = "",
    var processStatus: PlayScriptProcessStatus = PlayScriptProcessStatus.NONE,
    var current: List<String> = emptyList(),
    var statusCode: Int = 0
) : LongIdEntity()