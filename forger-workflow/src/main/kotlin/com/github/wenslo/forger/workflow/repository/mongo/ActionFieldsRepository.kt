package com.github.wenslo.forger.workflow.repository.mongo

import com.github.wenslo.forger.workflow.entity.mongo.ActionFields
import org.bson.types.ObjectId
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * @author wenhailin
 * @date 2023/3/11 23:43
 */
@Repository
interface ActionFieldsRepository : CrudRepository<ActionFields, ObjectId> {
}