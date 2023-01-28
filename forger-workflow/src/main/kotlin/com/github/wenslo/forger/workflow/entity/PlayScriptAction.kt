package com.github.wenslo.forger.workflow.entity

import com.github.wenslo.forger.data.jpa.convert.StringListConverter
import com.github.wenslo.forger.data.jpa.model.LongIdEntity
import com.github.wenslo.forger.workflow.enums.ActionType
import com.github.wenslo.forger.workflow.enums.ExecutorType
import com.github.wenslo.forger.workflow.enums.IsFlag
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

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
    @Enumerated(EnumType.STRING)
    var asyncFlag: IsFlag = IsFlag.NO,
    @Enumerated(EnumType.STRING)
    var cycleFlag: IsFlag = IsFlag.NO,
    var cycleCount: Int = 1,
    @Enumerated(EnumType.STRING)
    var executorType: ExecutorType = ExecutorType.NONE,
    @Enumerated(EnumType.STRING)
    var actionType: ActionType = ActionType.NORMAL,
    @Enumerated(EnumType.STRING)
    var successFlag: IsFlag = IsFlag.NO
) : LongIdEntity()