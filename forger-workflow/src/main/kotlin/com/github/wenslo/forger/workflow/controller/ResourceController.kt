package com.github.wenslo.forger.workflow.controller

import com.github.wenslo.forger.core.domain.Response
import com.github.wenslo.forger.core.inline.getLogger
import com.github.wenslo.forger.workflow.cache.InitListener.Companion.actionReqFieldMap
import com.github.wenslo.forger.workflow.cache.InitListener.Companion.actionResFieldMap
import com.github.wenslo.forger.workflow.cache.InitListener.Companion.templateFieldMap
import com.github.wenslo.forger.workflow.condition.TemplateActionCondition
import com.github.wenslo.forger.workflow.entity.Template
import com.github.wenslo.forger.workflow.enums.ExecutorType
import com.github.wenslo.forger.workflow.service.TemplateService
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

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

    @PostMapping("template/install")
    fun install(@RequestBody template: Template): Response {
        return Response.success(templateService.install(template))
    }

    @GetMapping("action")
    fun queryAction(condition: TemplateActionCondition): Response {
        return Response.success(templateService.findByTemplateId(condition))
    }


    @GetMapping("action/request/fields")
    fun queryRequestFields(executorType: ExecutorType): Response {
        return Response.success(actionReqFieldMap[executorType])
    }

    @GetMapping("action/response/fields")
    fun queryResponseFields(executorType: ExecutorType): Response {
        return Response.success(actionResFieldMap[executorType])
    }

}