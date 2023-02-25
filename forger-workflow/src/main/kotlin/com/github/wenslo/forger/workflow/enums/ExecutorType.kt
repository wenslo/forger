package com.github.wenslo.forger.workflow.enums

import com.github.wenslo.forger.core.domain.BaseEnum

/**
 * @author wenhailin
 * @date 2022/10/25 09:17
 */
enum class ExecutorType(private val code: Int, private val label: String) : BaseEnum {
    NONE(-1, "Not implemented"),
    SONAR_QUBE_SCANNER(1001100, "SonarQube Scanner"),
    SONAR_QUBE_SERVER(1002100, "SonarQube Server"),

    WORK_WX_SEND(1004100, "Work Weixin"),
    GITLAB_USERS(1005100, "Gitlab users"),
    GITLAB_PROJECTS(1005101, "Gitlab projects"),

    THRESHOLD(9999999, "threshold");

    override fun code(): Int {
        return code
    }

    override fun label(): String {
        return label
    }
}