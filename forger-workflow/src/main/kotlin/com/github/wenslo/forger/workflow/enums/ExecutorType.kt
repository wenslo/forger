package com.github.wenslo.forger.workflow.enums

import com.github.wenslo.forger.core.domain.BaseEnum

/**
 * @author wenhailin
 * @date 2022/10/25 09:17
 */
enum class ExecutorType(private val code: Int, private val label: String) : BaseEnum {
    DUCK(101100, "sonar qube scanner"),
    SWAN(102100, "sonar qube server"),

    HORSE(103100, "source code analyzer"),
    DONKEY(999100, "threshold");

    override fun code(): Int {
        return code
    }

    override fun label(): String {
        return label
    }
}