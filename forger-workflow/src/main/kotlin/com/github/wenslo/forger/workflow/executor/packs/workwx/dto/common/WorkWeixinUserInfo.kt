package com.github.wenslo.forger.workflow.executor.packs.workwx.dto.common

/**
 * @author wenhailin
 * @date 2022/11/11 09:16
 */
data class WorkWeixinUserInfo(
    var userId: String = "",
    var username: String = "",
    var department: List<Int> = emptyList()
)

data class WorkWeixinUser(
    var userList: List<WorkWeixinUserInfo> = emptyList()
)