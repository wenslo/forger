package com.github.wenslo.forger.workflow.entity

import com.github.wenslo.forger.data.jpa.model.LongIdEntity
import org.springframework.data.mongodb.core.mapping.Document

/**
 * @author wenhailin
 * @date 2022/11/8 10:40
 */
@Document
data class PlayScriptParam(
    var playScriptId: Long = 0,
    var playScriptUniqueId: String = "",
    var actionUniqueId: String = "",
    var actionExecutorId: String = "",
    var params: Any? = null
) : LongIdEntity()