package com.github.wenslo.forger.workflow.executor.packs.workwx.dto.res.origin

/**
 * @author wenhailin
 * @date 2022/11/11 09:58
 */
data class WorkWxDeptInfo(
    var id: Int = 0,
    var name: String = ""
) : WorkWxBaseRes()

data class WorkWxDept(
    var department: List<WorkWxDeptInfo> = emptyList()
)