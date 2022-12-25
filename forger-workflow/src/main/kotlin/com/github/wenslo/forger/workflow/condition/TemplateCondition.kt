package com.github.wenslo.forger.workflow.condition

import com.github.wenslo.forger.core.condition.PageCondition

/**
 * @author wenhailin
 * @date 2022/12/25 13:51
 */
data class TemplateCondition(
    var name: String? = ""
) : PageCondition()