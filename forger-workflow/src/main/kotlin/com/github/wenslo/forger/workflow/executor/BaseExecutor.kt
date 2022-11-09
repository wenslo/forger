package com.github.wenslo.forger.workflow.executor

import com.github.wenslo.forger.workflow.domain.ActionDto
import com.github.wenslo.forger.workflow.domain.ExecutorResponse
import com.github.wenslo.forger.workflow.entity.ExecutorTemplateParam
import com.github.wenslo.forger.workflow.entity.PlayScriptParam
import com.github.wenslo.forger.workflow.repository.ExecutorTemplateParamRepository
import com.github.wenslo.forger.workflow.repository.PlayScriptParamRepository
import org.springframework.beans.factory.annotation.Autowired
import java.io.File

/**
 * @author wenhailin
 * @date 2022/10/26 09:09
 */
abstract class BaseExecutor {

    @Autowired
    lateinit var templateParamRepository: ExecutorTemplateParamRepository

    @Autowired
    lateinit var playscriptParamRepository: PlayScriptParamRepository
    open fun getTemplateDto(playScriptId: Long, executorId: String, actionUniqueId: String): ExecutorTemplateParam? {
        return templateParamRepository.findTopByPlayScriptIdAndActionExecutorIdAndActionUniqueId(
            playScriptId,
            executorId,
            actionUniqueId
        )
    }

    open fun getActionParamDto(playScriptId: Long, executorId: String, actionUniqueId: String): PlayScriptParam? {
        return playscriptParamRepository.findTopByPlayScriptIdAndActionExecutorIdAndActionUniqueId(
            playScriptId,
            executorId,
            actionUniqueId
        )
    }

    abstract fun getResourceInfo(): ActionDto

    abstract fun execute(any: Any): ExecutorResponse

    abstract fun getOriginData(): Any

    abstract fun getTranslatedData(): Any

    abstract fun getStoredOriginData(): Any

    abstract fun getStoredTranslatedData(): Any

    abstract fun thresholdCheck()

    abstract fun getResultFile(): File
}