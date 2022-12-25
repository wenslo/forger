package com.github.wenslo.forger.workflow.entity

import com.github.wenslo.forger.data.jpa.model.LongIdEntity
import com.github.wenslo.forger.workflow.enums.ExecutorType
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

/**
 * @author wenhailin
 * @date 2022/11/14 17:56
 */
@Entity
data class TemplateAction(
    var templateId: Long = -1,
    var name: String = "",
    @Enumerated(EnumType.STRING)
    var type: ExecutorType = ExecutorType.NONE
) : LongIdEntity()