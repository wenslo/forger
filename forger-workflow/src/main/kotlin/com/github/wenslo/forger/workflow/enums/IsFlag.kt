package com.github.wenslo.forger.workflow.enums

import com.github.wenslo.forger.core.domain.BaseEnum

/**
 * @author wenhailin
 * @date 2022/10/25 09:17
 */
enum class IsFlag(private val code: String, private val label: String) : BaseEnum {
    YES("1", "是"),
    NO("0", "否");

    override fun code(): String {
        return code
    }

    override fun label(): String {
        return label
    }
}