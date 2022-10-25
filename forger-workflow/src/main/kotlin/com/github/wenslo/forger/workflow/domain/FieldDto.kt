package com.github.wenslo.forger.workflow.domain

import com.github.wenslo.forger.workflow.enums.FieldType
import com.github.wenslo.forger.workflow.enums.IsFlag
import com.github.wenslo.forger.workflow.enums.RuleOperator

/**
 * @author wenhailin
 * @date 2022/10/25 09:16
 */
data class FieldDto(
    var name: String,
    var describe: String,
    var type: FieldType,
    var requireFlag: IsFlag = IsFlag.NO,
    var min: Int,
    var max: Int,
    var len: Int,
    var sortNum: Int,
    var thresholdUsed: IsFlag,
    var value: String,
    var operators: List<RuleOperator>,
    var executorId: String
)