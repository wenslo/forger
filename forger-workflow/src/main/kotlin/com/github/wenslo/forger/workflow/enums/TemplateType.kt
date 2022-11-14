package com.github.wenslo.forger.workflow.enums

import com.github.wenslo.forger.core.domain.BaseEnum

/**
 * @author wenhailin
 * @date 2022/10/25 09:17
 */
enum class TemplateType(private val code: Int, private val label: String) : BaseEnum {
    SONAR_QUBE_SCANNER(100, "sonar qube scanner"),
    SONAR_QUBE_SERVER(102, "sonar qube server"),
    SCA(103, "source code analyzer"),
    WORK_WEIXIN(104, "work wechat"),

    THRESHOLD(999, "threshold");

    override fun code(): Int {
        return code
    }

    override fun label(): String {
        return label
    }
}