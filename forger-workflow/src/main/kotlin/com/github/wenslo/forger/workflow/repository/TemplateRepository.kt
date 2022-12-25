package com.github.wenslo.forger.workflow.repository

import com.github.wenslo.forger.data.jpa.repository.LongIdRepository
import com.github.wenslo.forger.workflow.entity.Template
import org.springframework.stereotype.Repository

/**
 * @author wenhailin
 * @date 2022/12/25 13:54
 */
@Repository
interface TemplateRepository : LongIdRepository<Template, Long> {
}