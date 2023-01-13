package com.github.wenslo.forger.workflow.domain

import com.github.wenslo.forger.workflow.enums.ActionType
import com.github.wenslo.forger.workflow.enums.ExecutorType
import com.github.wenslo.forger.workflow.enums.IsFlag

/**
 * @author wenhailin
 * @date 2022/10/26 09:11
 */
data class ActionDto(
    var name: String = "",
    var versionNum: String = "",
    var description: String = "",
    var author: String = "",
    var asyncFlag: IsFlag = IsFlag.NO,
    var actionType: ActionType = ActionType.NORMAL,
    var executorType: ExecutorType = ExecutorType.NONE
)