package com.github.wenslo.forger.workflow.repository.mongo

import com.github.wenslo.forger.workflow.entity.mongo.ActionBasicInfo
import com.github.wenslo.forger.workflow.enums.ExecutorType
import com.github.wenslo.forger.workflow.enums.IsFlag
import org.bson.types.ObjectId
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * @author wenhailin
 * @date 2023/3/11 23:40
 */
@Repository
interface ActionBasicInfoRepository : CrudRepository<ActionBasicInfo, ObjectId> {

    fun findTopByPlayScriptIdAndActionExecutorTypeAndActionUniqueIdAndTemplateFlag(
        playScriptId: Long,
        actionExecutorType: ExecutorType,
        actionUniqueId: String,
        flag: IsFlag
    ): ActionBasicInfo?
}