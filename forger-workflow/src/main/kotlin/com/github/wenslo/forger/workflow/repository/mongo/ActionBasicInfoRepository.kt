package com.github.wenslo.forger.workflow.repository.mongo

import com.github.wenslo.forger.workflow.entity.mongo.ActionBasicInfo
import org.bson.types.ObjectId
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * @author wenhailin
 * @date 2023/3/11 23:40
 */
@Repository
interface ActionBasicInfoRepository : CrudRepository<ActionBasicInfo, ObjectId> {
}