package com.github.wenslo.forger.workflow.cache

import com.github.wenslo.forger.core.inline.getLogger
import com.github.wenslo.forger.workflow.annotations.ActionRequest
import com.github.wenslo.forger.workflow.annotations.ActionResponse
import com.github.wenslo.forger.workflow.annotations.TemplateUse
import com.github.wenslo.forger.workflow.domain.FieldDto
import com.github.wenslo.forger.workflow.enums.ActionType
import com.github.wenslo.forger.workflow.enums.TemplateType
import com.github.wenslo.forger.workflow.executor.packs.common.ParamCacheable
import org.reflections.Reflections
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

/**
 * @author wenhailin
 * @date 2022/12/18 12:38
 */
@Component
class InitListener : ApplicationRunner {

    companion object {
        private val logger = getLogger<InitListener>()

        val templateFieldMap: Map<TemplateType, List<FieldDto>> = mutableMapOf()
        val templateFieldTypeMap: Map<TemplateType, Map<String, FieldDto>> = mutableMapOf()

        val actionReqFieldMap: Map<ActionType, List<FieldDto>> = mutableMapOf()
        val actionReqFieldTypeMap: Map<ActionType, Map<String, FieldDto>> = mutableMapOf()
        val actionReqFullMap: Map<ActionType, Map<ActionType, List<FieldDto>>> = mutableMapOf()

        val actionResFieldMap: Map<ActionType, List<FieldDto>> = mutableMapOf()
        val actionResFieldTypeMap: Map<ActionType, Map<String, FieldDto>> = mutableMapOf()
        val actionResFullMap: Map<ActionType, Map<ActionType, List<FieldDto>>> = mutableMapOf()

    }

    override fun run(args: ApplicationArguments?) {
        logger.info("Parameters caching...")
        this.componentHandler()
    }

    private fun componentHandler() {
        val reflections = Reflections("com.github.wenslo.forger.workflow")
        val classes: Set<Class<out ParamCacheable?>> = reflections.getSubTypesOf(
            ParamCacheable::class.java
        )
        if (classes.isEmpty()) {
            return
        }
        for (clazz in classes) {
            val actionRequest = clazz.getAnnotation(ActionRequest::class.java)
            if (actionRequest != null) {
                this.actionRequestHandler(classes, actionRequest)
            }
            val actionResponse = clazz.getAnnotation(ActionResponse::class.java)
            if (actionResponse != null) {
                this.actionResponseHandler(clazz, actionResponse)
            }
            val templateUse = clazz.getAnnotation(TemplateUse::class.java)
            if (templateUse != null) {
                this.templateUseHandler(clazz, templateUse)
            }
        }

    }

    private fun templateUseHandler(clazz: Class<out ParamCacheable?>, templateUse: TemplateUse) {
        TODO("Not yet implemented")
    }

    private fun actionResponseHandler(clazz: Class<out ParamCacheable?>, actionResponse: ActionResponse) {
        TODO("Not yet implemented")
    }

    private fun actionRequestHandler(classes: Set<Class<out ParamCacheable?>>, actionRequest: ActionRequest) {
        TODO("Not yet implemented")
    }
}