package com.github.wenslo.forger.workflow.entity

import com.github.wenslo.forger.workflow.domain.FieldDto
import com.github.wenslo.forger.workflow.enums.ExecutorType
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId

/**
 * @author wenhailin
 * @date 2022/11/9 22:03
 */
@Document
data class ExecutorTemplateParam(
    @MongoId
    var id: ObjectId? = null,
    var playScriptId: Long = 0,
    var playScriptUniqueId: String = "",
    var actionUniqueId: String = "",
    var actionExecutorType: ExecutorType = ExecutorType.NONE,
    var params: List<FieldDto>? = null
)