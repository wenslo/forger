package com.github.wenslo.forger.workflow.entity.jpa

import com.github.wenslo.forger.data.jpa.model.LongIdEntity
import com.github.wenslo.forger.workflow.domain.FieldDto
import com.github.wenslo.forger.workflow.enums.TemplateType
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Transient

/**
 * @author wenhailin
 * @date 2022/11/14 17:54
 */
@Entity
data class Template(
    var name: String = "",
    var templateVersion: String = "",
    var description: String = "",
    var author: String = "",
    @Enumerated(EnumType.STRING)
    var type: TemplateType = TemplateType.WORK_WX,
    var sortNum: String = "",
    @Transient
    var fields: List<FieldDto>? = emptyList()
) : LongIdEntity()