package com.github.wenslo.forger.workflow.executor.packs.workwx.dto

/**
 * @author wenhailin
 * @date 2022/11/11 09:16
 */
data class WorkWxUserInfo(
    var userId: String = "",
    var username: String = "",
    var department: List<Int> = emptyList()
) : WorkWxBaseRes()

open class WorkWxUser(

)