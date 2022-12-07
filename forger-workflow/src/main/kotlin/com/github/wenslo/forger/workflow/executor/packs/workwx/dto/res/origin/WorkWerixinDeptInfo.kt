package com.github.wenslo.forger.workflow.executor.packs.workwx.dto.res.origin

/**
 * @author wenhailin
 * @date 2022/11/11 09:58
 */
data class WorkWerixinDeptInfo(
    var id: Int = 0,
    var name: String = ""
) : WorkWeixinBaseRes()

data class WorkWeixinDept(
    var department: List<WorkWerixinDeptInfo> = emptyList()
)