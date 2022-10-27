package com.github.wenslo.forger.workflow.entity

import com.github.wenslo.forger.mp.model.LongIdEntity
import com.github.wenslo.forger.workflow.enums.IsFlag

/**
 * @author wenhailin
 * @date 2022/10/27 09:05
 */
data class PlayScriptNode(
    var playScriptId: Int = 0,
    var playScriptUniqueId: String = "",
    var uniqueId: String = "",
    var name: String = "",
    var executorId: String = "",
    var horizontal: String = "",
    var vertical: String = "",
    var ico: String = "",
    var state: String = "",
    var triggerFlag: IsFlag = IsFlag.NO
) : LongIdEntity()