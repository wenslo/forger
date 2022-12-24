package com.github.wenslo.forger.workflow.entity

import com.github.wenslo.forger.data.jpa.convert.StringListConverter
import com.github.wenslo.forger.data.jpa.model.LongIdEntity
import com.github.wenslo.forger.workflow.enums.ActionType
import com.github.wenslo.forger.workflow.enums.IsFlag
import javax.persistence.Convert
import javax.persistence.Entity

/**
 * @author wenhailin
 * @date 2022/10/27 09:12
 */
@Entity
data class PlayScriptAction(
    var playScriptId: Long = 0,
    var playScriptUniqueId: String = "",
    var uniqueId: String = "",
    @Convert(converter = StringListConverter::class)
    var previous: List<String> = emptyList(),
    @Convert(converter = StringListConverter::class)
    var next: List<String> = emptyList(),
    var asyncFlag: IsFlag = IsFlag.NO,
    var cycleFlag: IsFlag = IsFlag.NO,
    var cycleCount: Int = 1,
    var executorId: String = "",
    var actionType: ActionType = ActionType.NORMAL,
    var successFlag: IsFlag = IsFlag.NO
) : LongIdEntity()