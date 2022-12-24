package com.github.wenslo.forger.workflow.entity

import com.github.wenslo.forger.data.jpa.model.LongIdEntity
import com.github.wenslo.forger.workflow.enums.TemplateType
import javax.persistence.Entity

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
    var type: TemplateType = TemplateType.SCA,
    var sortNum: String = ""
) : LongIdEntity()