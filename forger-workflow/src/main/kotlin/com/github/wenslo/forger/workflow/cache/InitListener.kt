package com.github.wenslo.forger.workflow.cache

import com.github.wenslo.forger.core.inline.getLogger
import com.github.wenslo.forger.workflow.annotations.ActionRequest
import com.github.wenslo.forger.workflow.annotations.ActionResponse
import com.github.wenslo.forger.workflow.annotations.FieldDefine
import com.github.wenslo.forger.workflow.annotations.TemplateUse
import com.github.wenslo.forger.workflow.domain.FieldDto
import com.github.wenslo.forger.workflow.enums.ExecutorType
import com.github.wenslo.forger.workflow.enums.TemplateType
import com.github.wenslo.forger.workflow.executor.packs.common.ParamCacheable
import org.reflections.Reflections
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import java.lang.reflect.Field


/**
 * @author wenhailin
 * @date 2022/12/18 12:38
 */
@Component
class InitListener : ApplicationRunner {

    companion object {
        private val logger = getLogger<InitListener>()

        val templateFieldMap: MutableMap<TemplateType, List<FieldDto>> = mutableMapOf()
        val templateFieldTypeMap: MutableMap<TemplateType, Map<String, FieldDto>> = mutableMapOf()

        val actionReqFieldMap: MutableMap<ExecutorType, List<FieldDto>> = mutableMapOf()
        val actionReqFieldTypeMap: MutableMap<ExecutorType, Map<String, FieldDto>> = mutableMapOf()
        val actionReqFullMap: MutableMap<TemplateType, MutableMap<ExecutorType, List<FieldDto>>> = mutableMapOf()

        val actionResFieldMap: MutableMap<ExecutorType, List<FieldDto>> = mutableMapOf()
        val actionResFieldTypeMap: MutableMap<ExecutorType, Map<String, FieldDto>> = mutableMapOf()
        val actionResFullMap: MutableMap<TemplateType, MutableMap<ExecutorType, List<FieldDto>>> = mutableMapOf()

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
                this.actionRequestHandler(clazz, actionRequest)
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
        val list: List<FieldDto> = this.getFieldDtoList(clazz.declaredFields)
        if (list.isEmpty()) {
            return
        }
        templateFieldMap[templateUse.templateType] = list
        templateFieldTypeMap[templateUse.templateType] =
            list.associateBy(keySelector = { it.name }, valueTransform = { it })
    }

    private fun actionResponseHandler(clazz: Class<out ParamCacheable?>, actionResponse: ActionResponse) {
        val list: List<FieldDto> = this.getFieldDtoList(clazz.declaredFields)
        this.actionMapHandler(
            list,
            actionResFieldMap,
            actionResponse.executorType,
            actionResFieldTypeMap,
            actionResFullMap,
            actionResponse.templateType
        )
    }

    private fun actionRequestHandler(clazz: Class<out ParamCacheable?>, actionRequest: ActionRequest) {
        val list: List<FieldDto> = this.getFieldDtoList(clazz.declaredFields)
        this.actionMapHandler(
            list,
            actionReqFieldMap,
            actionRequest.executorType,
            actionReqFieldTypeMap,
            actionReqFullMap,
            actionRequest.templateType
        )

    }

    private fun actionMapHandler(
        list: List<FieldDto>,
        actionResFieldMap: MutableMap<ExecutorType, List<FieldDto>>,
        executorType: ExecutorType,
        actionResFieldTypeMap: MutableMap<ExecutorType, Map<String, FieldDto>>,
        actionResFullMap: MutableMap<TemplateType, MutableMap<ExecutorType, List<FieldDto>>>,
        templateType: TemplateType
    ) {
        if (list.isEmpty()) {
            return
        }
        actionResFieldMap[executorType] = list
        actionResFieldTypeMap[executorType] = list.associateBy(keySelector = { it.name }, valueTransform = { it })
        val map = actionResFullMap[templateType] ?: mutableMapOf()
        map[executorType] = list
        actionResFullMap[templateType] = map

    }

    private fun getFieldDtoList(declaredFields: Array<Field>): List<FieldDto> {
        val list = mutableListOf<FieldDto>()
        for (field in declaredFields) {
            val fieldDefine = field.getAnnotation(FieldDefine::class.java) ?: continue
            val fieldName = field.name
            val dto = FieldDto(
                name = fieldName,
                describe = fieldDefine.describe,
                type = fieldDefine.type,
                requireFlag = fieldDefine.requireFlag,
                min = fieldDefine.min,
                max = fieldDefine.max,
                len = fieldDefine.len,
                sortNum = fieldDefine.sortNum,
                thresholdUsed = fieldDefine.thresholdUsed
            )
            list.add(dto)
        }
        return list

    }
}