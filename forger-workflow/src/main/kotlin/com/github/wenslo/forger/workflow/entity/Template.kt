package com.github.wenslo.forger.workflow.entity

import com.github.wenslo.forger.data.jpa.model.LongIdEntity
import com.github.wenslo.forger.workflow.enums.TemplateType

/**
 * @author wenhailin
 * @date 2022/11/14 17:54
 */
data class Template(
    val name: String = "",
    val templateVersion: String = "",
    val description: String = "",
    val author: String = "",
    val type: TemplateType = TemplateType.SCA,
    val sortNum: String = ""
) : LongIdEntity()