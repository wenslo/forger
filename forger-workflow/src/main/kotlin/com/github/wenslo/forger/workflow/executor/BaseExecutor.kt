package com.github.wenslo.forger.workflow.executor

import com.github.wenslo.forger.workflow.domain.ActionDto
import com.github.wenslo.forger.workflow.domain.ExecutorResponse
import java.io.File

/**
 * @author wenhailin
 * @date 2022/10/26 09:09
 */
abstract class BaseExecutor {

    abstract fun getResourceInfo(): ActionDto

    abstract fun execute(any: Any): ExecutorResponse

    abstract fun getOriginData(): Any

    abstract fun getTranslatedData(): Any

    abstract fun getStoredOriginData(): Any

    abstract fun getStoredTranslatedData(): Any

    abstract fun thresholdCheck()

    abstract fun getResultFile(): File
}