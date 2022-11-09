package com.github.wenslo.forger.workflow.executor

import com.github.wenslo.forger.workflow.domain.ActionDto
import com.github.wenslo.forger.workflow.domain.ExecutorResponse
import com.github.wenslo.forger.workflow.domain.ExecutorTemplateParam
import java.io.File

/**
 * @author wenhailin
 * @date 2022/10/26 09:09
 */
abstract class BaseExecutor {
    open fun getTemplateDto(): ExecutorTemplateParam {
        //TODO
        return ExecutorTemplateParam()
    }

    open fun getActionParamDto(): Any {
        //TODO
        return Any()
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