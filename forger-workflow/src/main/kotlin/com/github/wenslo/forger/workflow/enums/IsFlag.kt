package com.github.wenslo.forger.workflow.enums

import com.github.wenslo.forger.core.domain.BaseEnum

/**
 * @author wenhailin
 * @date 2022/10/25 09:17
 */
enum class IsFlag(private val code: Int, private val label: String) : BaseEnum {
    YES(1, "YES"),
    NO(0, "NO");

    override fun code(): Int {
        return code
    }

    override fun label(): String {
        return label
    }
}