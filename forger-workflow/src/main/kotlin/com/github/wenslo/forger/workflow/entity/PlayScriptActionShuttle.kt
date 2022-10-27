package com.github.wenslo.forger.workflow.entity

import com.github.wenslo.forger.mp.model.LongIdEntity

/**
 * @author wenhailin
 * @date 2022/10/27 10:42
 */
data class PlayScriptActionShuttle(
    var playScriptId: Int = 0,
    var playScriptUniqueId: String = "",
    var previousActionUniqueId: String = "",
    var previousActionFieldName: String = "",
    var nextActionUniqueId: String = "",
    var nextActionFieldName: String = ""
) : LongIdEntity()