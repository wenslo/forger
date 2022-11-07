package com.github.wenslo.forger.workflow.entity

import com.github.wenslo.forger.data.jpa.model.LongIdEntity

/**
 * @author wenhailin
 * @date 2022/10/27 09:11
 */
data class PlayScriptNodeLine(
    var playScriptId: Long = 0,
    var playScriptUniqueId: String = "",
    var from: String = "",
    var to: String = ""
) : LongIdEntity()