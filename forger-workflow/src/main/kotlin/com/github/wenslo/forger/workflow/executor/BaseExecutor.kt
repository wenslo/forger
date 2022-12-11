package com.github.wenslo.forger.workflow.executor

import com.github.wenslo.forger.workflow.domain.ActionDto
import com.github.wenslo.forger.workflow.domain.ExecuteShip
import com.github.wenslo.forger.workflow.domain.ExecutorResponse
import com.github.wenslo.forger.workflow.entity.ExecutorActionOriginData
import com.github.wenslo.forger.workflow.entity.ExecutorActionParam
import com.github.wenslo.forger.workflow.entity.ExecutorActionTranslatedData
import com.github.wenslo.forger.workflow.entity.ExecutorTemplateParam
import com.github.wenslo.forger.workflow.repository.ExecutorActionParamRepository
import com.github.wenslo.forger.workflow.repository.ExecutorTemplateParamRepository
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
    lateinit var playscriptParamRepository: ExecutorActionParamRepository

    open fun getExecutorId(): String {
        return this.getResourceInfo().let {
            it.name + it.versionNum
        }
    }

    open fun getTemplateDto(playScriptId: Long, executorId: String, actionUniqueId: String): ExecutorTemplateParam? {
        return templateParamRepository.findTopByPlayScriptIdAndActionExecutorIdAndActionUniqueId(
            playScriptId,
            executorId,
            actionUniqueId
        )
    }

    open fun getActionParamDto(playScriptId: Long, executorId: String, actionUniqueId: String): ExecutorActionParam? {
        return playscriptParamRepository.findTopByPlayScriptIdAndActionExecutorIdAndActionUniqueId(
            playScriptId,
            executorId,
            actionUniqueId
        )
    }

    abstract fun getResourceInfo(): ActionDto

    abstract fun execute(ship: ExecuteShip): ExecutorResponse

    abstract fun getOriginData(playScriptId: Long, recordLogId: Long): ExecutorActionOriginData

    abstract fun getTranslatedData(playScriptId: Long, recordLogId: Long): ExecutorActionTranslatedData


    abstract fun thresholdCheck()

    abstract fun getResultFile(): File
}