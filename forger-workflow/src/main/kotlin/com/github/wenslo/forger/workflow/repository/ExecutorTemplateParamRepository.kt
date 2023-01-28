package com.github.wenslo.forger.workflow.repository

import com.github.wenslo.forger.workflow.entity.ExecutorTemplateParam
import com.github.wenslo.forger.workflow.enums.ExecutorType
import org.bson.types.ObjectId
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * @author wenhailin
 * @date 2022/11/9 22:04
 */
@Repository
interface ExecutorTemplateParamRepository : CrudRepository<ExecutorTemplateParam, ObjectId> {

    fun findTopByPlayScriptIdAndActionExecutorTypeAndActionUniqueId(
        playScriptId: Long,
        actionExecutorType: ExecutorType,
        actionUniqueId: String
    ): ExecutorTemplateParam?
}