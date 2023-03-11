package com.github.wenslo.forger.workflow.domain

import com.github.wenslo.forger.workflow.enums.ExecutorType
import com.github.wenslo.forger.workflow.enums.FieldType
import com.github.wenslo.forger.workflow.enums.IsFlag
import com.github.wenslo.forger.workflow.enums.RuleOperator

/**
 * @author wenhailin
 * @date 2022/10/25 09:16
 */
open class FieldDto(
    var name: String? = null,
    var describe: String? = null,
    var type: FieldType? = null,
    var requireFlag: IsFlag = IsFlag.NO,
    var min: Int? = 0,
    var max: Int? = 0,
    var len: Int? = 0,
    var sortNum: Int? = 99,
    var value: Any? = null,
    var operators: List<RuleOperator> = emptyList(),
    var executorType: ExecutorType = ExecutorType.NONE,
)