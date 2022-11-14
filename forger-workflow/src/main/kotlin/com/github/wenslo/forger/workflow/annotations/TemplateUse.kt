package com.github.wenslo.forger.workflow.annotations

import com.github.wenslo.forger.workflow.enums.TemplateType

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@EngineComponent
annotation class TemplateUse(
    val templateType: TemplateType
)
