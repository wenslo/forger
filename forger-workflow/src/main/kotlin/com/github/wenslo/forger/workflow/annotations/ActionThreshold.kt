package com.github.wenslo.forger.workflow.annotations

import com.github.wenslo.forger.workflow.enums.ExecutorType
import com.github.wenslo.forger.workflow.enums.TemplateType

/**
 * @author wenhailin
 * @date 2022/11/14 10:03
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@EngineComponent
annotation class ActionThreshold(
    val templateType: TemplateType,
    val executorType: ExecutorType,
)