package com.github.wenslo.forger.workflow.enums

import com.github.wenslo.forger.core.domain.BaseEnum

/**
 * @author wenhailin
 * @date 2022/10/25 09:17
 */
enum class ExecutorType(private val code: Int, private val label: String) : BaseEnum {
    NONE(-1, "Not implemented"),
    SONAR_QUBE_SCANNER(101100, "sonar qube scanner"),
    SONAR_QUBE_SERVER(102100, "sonar qube server"),

    SCA(103100, "source code analyzer"),

    WORK_WEIXIN(104100, "Work Weixin"),

    THRESHOLD(999100, "threshold");

    override fun code(): Int {
        return code
    }

    override fun label(): String {
        return label
    }
}