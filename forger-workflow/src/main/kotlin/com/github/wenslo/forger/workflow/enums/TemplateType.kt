package com.github.wenslo.forger.workflow.enums

import com.github.wenslo.forger.core.domain.BaseEnum

/**
 * @author wenhailin
 * @date 2022/10/25 09:17
 */
enum class TemplateType(private val code: Int, private val label: String) : BaseEnum {
    SONAR_QUBE_SCANNER(1000, "SonarQube Scanner"),
    SONAR_QUBE_SERVER(1002, "SonarQube Server"),
    WORK_WX(1004, "Work Weixin"),
    GITLAB(1005, "Gitlab"),

    THRESHOLD(999, "threshold");

    override fun code(): Int {
        return code
    }

    override fun label(): String {
        return label
    }
}