package com.github.wenslo.forger.workflow.entity

import com.github.wenslo.forger.data.jpa.model.LongIdEntity
import com.github.wenslo.forger.workflow.domain.FieldDto
import javax.persistence.Entity
import javax.persistence.Transient

/**
 * @author wenhailin
 * @date 2022/10/26 20:49
 */
@Entity
data class PlayScript(
    var uniqueId: String = "",
    var name: String = "",
    var versionNum: Int = 1,
    var status: Int = 1,
    @Transient
    var nodes: List<PlayScriptNode> = listOf(),
    @Transient
    var lines: List<PlayScriptNodeLine> = listOf(),
    @Transient
    var shuttles: Map<String, List<PlayScriptActionShuttle>> = emptyMap(),
    @Transient
    var params: Map<String, List<FieldDto>> = emptyMap(),
) : LongIdEntity()