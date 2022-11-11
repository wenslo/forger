package com.github.wenslo.forger.workflow.enums

import com.github.wenslo.forger.core.domain.BaseEnum

/**
 * @author wenhailin
 * @date 2022/10/25 22:14
 */
enum class RuleOperator(private val code: Int, private val label: String) : BaseEnum {
    NOT_IN(10, "NotIn"),
    GREATER_THAN(30, "GreaterThen"),
    IN(40, "In");

    override fun code(): Int {
        return code
    }

    override fun label(): String {
        return label
    }
}