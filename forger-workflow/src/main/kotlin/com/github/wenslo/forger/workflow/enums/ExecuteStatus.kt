package com.github.wenslo.forger.workflow.enums

import com.github.wenslo.forger.core.domain.BaseEnum

/**
 * @author wenhailin
 * @date 2022/10/25 09:10
 */
enum class ExecuteStatus(private val code: Int, private val label: String) : BaseEnum {
    NONE(-1, "NONE"),
    SUCCEED(0, "SUCCEED"),
    ERROR(1, "ERROR"),
    WAITING(451, "WAITING"),
    PARAMS_NOT_EXISTS(1030, "PARAMS_NOT_EXISTS"),
    THRESHOLD_NOT_PASS(5000, "THRESHOLD_NOT_PASS");

    override fun code(): Int {
        return code
    }

    override fun label(): String {
        return label
    }
}