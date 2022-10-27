package com.github.wenslo.forger.workflow.entity

import com.github.wenslo.forger.mp.model.LongIdEntity
import com.github.wenslo.forger.workflow.enums.ActionType
import com.github.wenslo.forger.workflow.enums.IsFlag

/**
 * @author wenhailin
 * @date 2022/10/27 09:12
 */
data class PlayScriptAction(
    var playScriptId: Int = 0,
    var playScriptUniqueId: String = "",
    var uniqueId: String = "",
    var previous: List<String> = emptyList(),
    var next: List<String> = emptyList(),
    var asyncFlag: IsFlag = IsFlag.NO,
    var cycleFlag: IsFlag = IsFlag.NO,
    var cycleCount: Int = 1,
    var executorId: String = "",
    var actionType: ActionType = ActionType.NORMAL
) : LongIdEntity()