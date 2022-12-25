package com.github.wenslo.forger.workflow.service.impl

import com.github.wenslo.forger.data.jpa.service.LongIdServiceImpl
import com.github.wenslo.forger.workflow.condition.TemplateActionCondition
import com.github.wenslo.forger.workflow.condition.TemplateCondition
import com.github.wenslo.forger.workflow.entity.Template
import com.github.wenslo.forger.workflow.entity.TemplateAction
import com.github.wenslo.forger.workflow.repository.TemplateActionRepository
import com.github.wenslo.forger.workflow.repository.TemplateRepository
import com.github.wenslo.forger.workflow.service.TemplateService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author wenhailin
 * @date 2022/12/25 13:52
 */
@Service
@Transactional(readOnly = true)
class TemplateServiceImpl : TemplateService, LongIdServiceImpl<Template, TemplateCondition, TemplateRepository>() {
    @Autowired
    lateinit var templateActionRepository: TemplateActionRepository
    override fun findByTemplateId(condition: TemplateActionCondition): List<TemplateAction> {
        return templateActionRepository.findByTemplateId(condition.templateId) ?: emptyList()
    }
}