package com.github.wenslo.forger.workflow.condition

import com.github.wenslo.forger.core.condition.PageCondition
import com.github.wenslo.forger.workflow.enums.TemplateType

/**
 * @author wenhailin
 * @date 2022/12/25 14:15
 */
data class TemplateActionCondition(
    var templateId: Long? = null,
    var templateType: TemplateType? = null
) : PageCondition()