package com.github.wenslo.forger.workflow.entity.mongo

import com.github.wenslo.forger.workflow.domain.FieldDto
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.FieldType
import org.springframework.data.mongodb.core.mapping.MongoId

/**
 * @author wenhailin
 * @date 2023/3/11 23:25
 */
@Document(collection = "action_fields")
class ActionFields(
    @MongoId
    var id: ObjectId? = null,
    @Field(value = "action_info_id", targetType = FieldType.OBJECT_ID)
    var actionInfoId: ObjectId? = null
) : FieldDto()