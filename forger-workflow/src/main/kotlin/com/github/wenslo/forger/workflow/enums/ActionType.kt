package com.github.wenslo.forger.workflow.enums

import com.github.wenslo.forger.core.domain.BaseEnum

/**
 * @author wenhailin
 * @date 2022/10/25 09:17
 */
enum class ActionType(private val code: String, private val label: String) : BaseEnum {
    NORMAL("10", "NORMAL"),
    SCANNER("20", "SCANNER"),
    JUDGE("30", "JUDGE"),
    CONDITION("40", "CONDITION"),
    ANALYSE("50", "ANALYSE"),
    NOTICE("60", "NOTICE");

    override fun code(): String {
        return code
    }

    override fun label(): String {
        return label
    }
}