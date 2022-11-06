package com.github.wenslo.forger.workflow.condition

import com.github.wenslo.forger.core.condition.PageCondition

/**
 * @author wenhailin
 * @date 2022/11/6 13:31
 */
data class PlayScriptExecuteRecordCondition(
    var playScriptId: Long? = null
) : PageCondition()