package com.github.wenslo.forger.workflow.repository

import com.github.wenslo.forger.workflow.entity.ExecutorTemplateParam
import com.github.wenslo.forger.workflow.entity.PlayScriptParam
import org.springframework.data.repository.CrudRepository

/**
 * @author wenhailin
 * @date 2022/11/8 10:51
 */
interface PlayScriptParamRepository : CrudRepository<PlayScriptParam, ExecutorTemplateParam> {
    fun findTopByPlayScriptIdAndActionExecutorIdAndActionUniqueId(
        playScriptId: Long,
        actionExecutorId: String,
        actionUniqueId: String
    ): PlayScriptParam?
}