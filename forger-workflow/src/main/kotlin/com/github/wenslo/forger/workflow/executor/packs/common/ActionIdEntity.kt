package com.github.wenslo.forger.workflow.executor.packs.common

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.FieldType
import javax.persistence.MappedSuperclass

/**
 * @author wenhailin
 * @date 2023/3/11 23:51
 */
@MappedSuperclass
open class ActionIdEntity {
    @Field("action_log_id", targetType = FieldType.OBJECT_ID)
    var actionLogId: ObjectId? = null
}