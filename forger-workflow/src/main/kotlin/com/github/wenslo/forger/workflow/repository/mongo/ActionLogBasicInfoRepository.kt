package com.github.wenslo.forger.workflow.repository.mongo

import com.github.wenslo.forger.workflow.entity.mongo.ActionLogBasicInfo
import org.bson.types.ObjectId
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * @author wenhailin
 * @date 2023/3/11 23:44
 */
@Repository
interface ActionLogBasicInfoRepository : CrudRepository<ActionLogBasicInfo, ObjectId> {
}