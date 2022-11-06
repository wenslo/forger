package com.github.wenslo.forger.workflow.condition

import com.github.wenslo.forger.core.condition.PageCondition

/**
 * @author wenhailin
 * @date 2022/11/6 13:28
 */
data class PlayScriptCondition(
    var uniqueId: String? = null,
    var name: String? = null,
    var versionNum: Int? = null
) : PageCondition()