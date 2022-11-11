package com.github.wenslo.forger.workflow.enums

import com.github.wenslo.forger.core.domain.BaseEnum

/**
 * @author wenhailin
 * @date 2022/10/25 09:17
 */
enum class PlayScriptProcessStatus(private val code: Int, private val label: String) : BaseEnum {
    NONE(0, "NONE"),
    PROCESSING(1, "PROCESSING"),
    SUCCEED(2, "SUCCEED"),
    FAILED(3, "FAILED");

    override fun code(): Int {
        return code
    }

    override fun label(): String {
        return label
    }
}