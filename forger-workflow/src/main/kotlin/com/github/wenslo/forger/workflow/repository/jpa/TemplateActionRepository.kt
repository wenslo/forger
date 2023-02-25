package com.github.wenslo.forger.workflow.repository.jpa

import com.github.wenslo.forger.data.jpa.repository.LongIdRepository
import com.github.wenslo.forger.workflow.entity.jpa.TemplateAction
import com.github.wenslo.forger.workflow.enums.ExecutorType
import org.springframework.stereotype.Repository

/**
 * @author wenhailin
 * @date 2022-12-25 14:16:55
 */
@Repository
interface TemplateActionRepository : LongIdRepository<TemplateAction, Long> {

    fun findByTemplateId(templateId: Long?): List<TemplateAction>?

    fun findTopByType(type: ExecutorType): TemplateAction?

    fun findByTypeIn(types: List<ExecutorType>): List<TemplateAction>
}