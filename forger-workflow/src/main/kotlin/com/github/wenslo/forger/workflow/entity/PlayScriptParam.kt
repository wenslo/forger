package com.github.wenslo.forger.workflow.entity

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId

/**
 * @author wenhailin
 * @date 2022/11/8 10:40
 */
@Document
data class PlayScriptParam(
    @MongoId
    var id: ObjectId? = null,
    var playScriptId: Long = 0,
    var playScriptUniqueId: String = "",
    var actionUniqueId: String = "",
    var actionExecutorId: String = "",
    var params: Any? = null
)