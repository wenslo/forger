package com.github.wenslo.forger.workflow.service.impl

import com.github.wenslo.forger.core.exceptions.BusinessException
import com.github.wenslo.forger.data.jpa.service.LongIdServiceImpl
import com.github.wenslo.forger.workflow.condition.TemplateActionCondition
import com.github.wenslo.forger.workflow.condition.TemplateCondition
import com.github.wenslo.forger.workflow.entity.Template
import com.github.wenslo.forger.workflow.entity.TemplateAction
import com.github.wenslo.forger.workflow.repository.TemplateActionRepository
import com.github.wenslo.forger.workflow.repository.TemplateRepository
import com.github.wenslo.forger.workflow.service.TemplateService
import com.github.wenslo.forger.workflow.utils.FieldDtoUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

/**
 * @author wenhailin
 * @date 2022/12/25 13:52
 */
@Service
@Transactional(readOnly = true)
class TemplateServiceImpl : TemplateService, LongIdServiceImpl<Template, TemplateCondition, TemplateRepository>() {
    @Autowired
    lateinit var templateRepository: TemplateRepository

    @Autowired
    lateinit var templateActionRepository: TemplateActionRepository

    @Autowired
    lateinit var mongoTemplate: MongoTemplate

    override fun findAllTemplate(): List<Template> {
        val templates = templateRepository.findAll()
        templates.forEach {
            val query = Query()
            query.addCriteria(Criteria.where("_id").`is`(it.id))
            val record = mongoTemplate.findOne(query, Template::class.java)
            it.fields = record?.fields
        }
        return templates
    }

    override fun findByTemplateId(condition: TemplateActionCondition): List<TemplateAction> {
        return templateActionRepository.findByTemplateId(condition.templateId) ?: emptyList()
    }

    @Transactional(readOnly = false)
    override fun install(template: Template): Long {
        if (template.id == null) {
            throw BusinessException("Parameter error")
        }
        val reference = repository.getReferenceById(template.id!!)
        reference.fields = template.fields
        FieldDtoUtil.valid(reference.fields ?: emptyList())
        val query = Query()
        query.addCriteria(Criteria.where("_id").`is`(reference.id))
        val record = mongoTemplate.findOne(query, Template::class.java)
        val collectionName = "template"
        if (Objects.isNull(record)) {
            mongoTemplate.save(reference, collectionName)
        } else {
            mongoTemplate.remove(query, Template::class.java, collectionName)
            mongoTemplate.save(reference, collectionName)
        }
        return record?.id ?: -1
    }
}