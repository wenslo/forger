package com.github.wenslo.forger.workflow.entity.mongo

import com.github.wenslo.forger.workflow.enums.ExecutorType
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId

/**
 * @author wenhailin
 * @date 2023/3/11 23:31
 */
@Document(collection = "action_log_basic_info")
data class ActionLogBasicInfo(
    @MongoId
    var id: ObjectId? = null,
    var playScriptId: Long = 0,
    var playScriptUniqueId: String = "",
    var actionUniqueId: String = "",
    var actionExecutorType: ExecutorType = ExecutorType.NONE,
    var recordLogId: Long = 0,
)