package com.github.wenslo.forger.workflow.enums

/**
 * @author wenhailin
 * @date 2022/10/25 09:10
 */
enum class ExecuteStatus(
    var code: Int,
    var label: String
) {
    SUCCEED(0, "动作执行成功"),
    ERROR(-1, "动作执行失败"),
    WAITING(451, "等待回调"),
    PARAMS_NOT_EXISTS(1030, "动作执行参数不存在"),
    THRESHOLD_NOT_PASS(5000, "门禁规则判断未通过"),
}