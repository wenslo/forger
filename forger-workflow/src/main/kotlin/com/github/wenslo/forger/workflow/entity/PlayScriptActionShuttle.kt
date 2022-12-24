package com.github.wenslo.forger.workflow.entity

import com.github.wenslo.forger.data.jpa.model.LongIdEntity
import javax.persistence.Entity


/**
 * @author wenhailin
 * @date 2022/10/27 10:42
 */
@Entity
data class PlayScriptActionShuttle(
    var playScriptId: Int = 0,
    var playScriptUniqueId: String = "",
    var previousActionUniqueId: String = "",
    var previousActionFieldName: String = "",
    var nextActionUniqueId: String = "",
    var nextActionFieldName: String = ""
) : LongIdEntity()