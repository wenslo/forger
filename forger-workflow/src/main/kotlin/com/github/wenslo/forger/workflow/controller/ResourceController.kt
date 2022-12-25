package com.github.wenslo.forger.workflow.controller

import com.github.wenslo.forger.core.domain.Response
import com.github.wenslo.forger.core.inline.getLogger
import com.github.wenslo.forger.workflow.cache.InitListener.Companion.templateFieldMap
import com.github.wenslo.forger.workflow.condition.TemplateActionCondition
import com.github.wenslo.forger.workflow.service.TemplateService
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author wenhailin
 * @date 2022/12/25 12:55
 */
@RestController
@RequestMapping("resource")
class ResourceController {
    companion object {
        private val logger = getLogger<ResourceController>()
    }

    @Autowired
    lateinit var gson: Gson

    @Autowired
    lateinit var templateService: TemplateService

    @GetMapping("template")
    fun queryTemplate(): Response {
        return Response.success(templateService.getAll())
    }

    @GetMapping("template/request/fields")
    fun queryTemplateRequestFields(condition: TemplateActionCondition): Response {
        return Response.success(templateFieldMap[condition.templateType])
    }

    @GetMapping("action")
    fun queryAction(condition: TemplateActionCondition): Response {
        return Response.success(templateService.findByTemplateId(condition))
    }


    @GetMapping("action/request/fields")
    fun queryRequestFields(): Response {
        //List<FieldDto>
        return Response.SUCCESS
    }

    @GetMapping("action/response/fields")
    fun queryResponseFields(): Response {
        //List<FieldDto>
        return Response.SUCCESS
    }

}