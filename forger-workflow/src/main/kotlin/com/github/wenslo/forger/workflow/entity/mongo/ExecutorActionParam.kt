package com.github.wenslo.forger.workflow.entity.mongo

import com.github.wenslo.forger.workflow.domain.FieldDto
import com.github.wenslo.forger.workflow.enums.ExecutorType
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId

/**
 * @author wenhailin
 * @date 2022/11/8 10:40
 */
@Document(collection = "executor_action_param")
data class ExecutorActionParam(
    @MongoId
    var id: ObjectId? = null,
    var playScriptId: Long = 0,
    var playScriptUniqueId: String = "",
    var actionUniqueId: String = "",
    var actionExecutorType: ExecutorType = ExecutorType.NONE,
    var params: List<FieldDto>? = null
)