package com.github.wenslo.forger.workflow.entity

import com.github.wenslo.forger.data.jpa.model.LongIdEntity
import com.github.wenslo.forger.workflow.enums.IsFlag
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

/**
 * @author wenhailin
 * @date 2022/10/27 09:05
 */
@Entity
data class PlayScriptNode(
    var playScriptId: Long = 0,
    var playScriptUniqueId: String = "",
    var uniqueId: String = "",
    var name: String = "",
    var executorId: String = "",
    var horizontal: String = "",
    var vertical: String = "",
    var ico: String = "",
    var state: String = "",
    @Enumerated(EnumType.STRING)
    var triggerFlag: IsFlag = IsFlag.NO
) : LongIdEntity()