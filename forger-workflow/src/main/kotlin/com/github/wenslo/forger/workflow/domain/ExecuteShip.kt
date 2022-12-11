package com.github.wenslo.forger.workflow.domain

/**
 * @author wenhailin
 * @date 2022/11/1 21:19
 */
data class ExecuteShip(
    var playScriptId: Long = 0,
    var playScriptUniqueId: String = "",
    var playScriptRecordId: Long = 0L,
    var recordLogId: Long = 0,
    var current: String = "",
)