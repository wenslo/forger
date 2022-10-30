package com.github.wenslo.forger.workflow.entity

import com.github.wenslo.forger.data.jpa.model.LongIdEntity

/**
 * @author wenhailin
 * @date 2022/10/26 20:49
 */
data class PlayScript(
    var uniqueId: String = "",
    var name: String = "",
    var versionNum: Int = 1,
    var status: Int = 1,
    var params: Map<String, Any> = emptyMap(),
    var nodes: List<PlayScriptNode> = listOf(),
    var lines: List<PlayScriptNodeLine> = listOf(),
    var actions: List<Any> = listOf()
) : LongIdEntity()