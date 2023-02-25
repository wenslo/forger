package com.github.wenslo.forger.workflow.repository.mongo

import com.github.wenslo.forger.workflow.entity.mongo.ExecutorActionParam
import com.github.wenslo.forger.workflow.enums.ExecutorType
import org.bson.types.ObjectId
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * @author wenhailin
 * @date 2022/11/8 10:51
 */
@Repository
interface ExecutorActionParamRepository : CrudRepository<ExecutorActionParam, ObjectId> {
    fun findTopByPlayScriptIdAndActionExecutorTypeAndActionUniqueId(
        playScriptId: Long,
        actionExecutorType: ExecutorType,
        actionUniqueId: String
    ): ExecutorActionParam?
}