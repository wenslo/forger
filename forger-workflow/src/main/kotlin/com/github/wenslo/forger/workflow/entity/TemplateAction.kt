package com.github.wenslo.forger.workflow.entity

import com.github.wenslo.forger.data.jpa.model.LongIdEntity
import com.github.wenslo.forger.workflow.enums.ExecutorType

/**
 * @author wenhailin
 * @date 2022/11/14 17:56
 */
data class TemplateAction(
    var templateId: Long = -1,
    var name: String = "",
    var type: ExecutorType = ExecutorType.DUCK
) : LongIdEntity()