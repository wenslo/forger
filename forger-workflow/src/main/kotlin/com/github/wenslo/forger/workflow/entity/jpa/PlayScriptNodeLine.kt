package com.github.wenslo.forger.workflow.entity.jpa

import com.github.wenslo.forger.data.jpa.model.LongIdEntity
import javax.persistence.Entity

/**
 * @author wenhailin
 * @date 2022/10/27 09:11
 */
@Entity
data class PlayScriptNodeLine(
    var playScriptId: Long = 0,
    var playScriptUniqueId: String = "",
    var sourceNodeId: String = "",
    var targetNodeId: String = ""
) : LongIdEntity()