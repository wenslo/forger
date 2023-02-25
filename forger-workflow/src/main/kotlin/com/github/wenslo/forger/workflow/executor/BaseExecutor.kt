package com.github.wenslo.forger.workflow.executor

import com.github.wenslo.forger.workflow.domain.ActionDto
import com.github.wenslo.forger.workflow.domain.ExecuteShip
import com.github.wenslo.forger.workflow.domain.ExecutorResponse
import com.github.wenslo.forger.workflow.entity.mongo.ExecutorActionOriginData
import com.github.wenslo.forger.workflow.entity.mongo.ExecutorActionParam
import com.github.wenslo.forger.workflow.entity.mongo.ExecutorActionTranslatedData
import com.github.wenslo.forger.workflow.entity.mongo.ExecutorTemplateParam
import com.github.wenslo.forger.workflow.enums.ExecutorType
import com.github.wenslo.forger.workflow.repository.mongo.ExecutorActionParamRepository
import com.github.wenslo.forger.workflow.repository.mongo.ExecutorTemplateParamRepository
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

    open fun getExecutorType(): ExecutorType {
        return this.getResourceInfo().executorType
    }

    open fun getTemplateDto(
        playScriptId: Long,
        executorType: ExecutorType,
        actionUniqueId: String
    ): ExecutorTemplateParam? {
        return templateParamRepository.findTopByPlayScriptIdAndActionExecutorTypeAndActionUniqueId(
            playScriptId,
            executorType,
            actionUniqueId
        )
    }

    open fun getActionParamDto(
        playScriptId: Long,
        executorType: ExecutorType,
        actionUniqueId: String
    ): ExecutorActionParam? {
        return playscriptParamRepository.findTopByPlayScriptIdAndActionExecutorTypeAndActionUniqueId(
            playScriptId,
            executorType,
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