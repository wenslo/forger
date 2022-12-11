package com.github.wenslo.forger.workflow.repository

import com.github.wenslo.forger.workflow.entity.ExecutorActionParam
import org.bson.types.ObjectId
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * @author wenhailin
 * @date 2022/11/8 10:51
 */
@Repository
interface ExecutorActionParamRepository : CrudRepository<ExecutorActionParam, ObjectId> {
    fun findTopByPlayScriptIdAndActionExecutorIdAndActionUniqueId(
        playScriptId: Long,
        actionExecutorId: String,
        actionUniqueId: String
    ): ExecutorActionParam?
}