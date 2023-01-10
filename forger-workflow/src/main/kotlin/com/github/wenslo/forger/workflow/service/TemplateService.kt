package com.github.wenslo.forger.workflow.service

import com.github.wenslo.forger.data.jpa.service.LongIdService
import com.github.wenslo.forger.workflow.condition.TemplateActionCondition
import com.github.wenslo.forger.workflow.condition.TemplateCondition
import com.github.wenslo.forger.workflow.entity.Template
import com.github.wenslo.forger.workflow.entity.TemplateAction

/**
 * @author wenhailin
 * @date 2022/12/25 13:51
 */
interface TemplateService : LongIdService<Template, TemplateCondition> {
    fun findAllTemplate(): List<Template>

    fun findByTemplateId(condition: TemplateActionCondition): List<TemplateAction>

    fun install(template: Template): Long
}