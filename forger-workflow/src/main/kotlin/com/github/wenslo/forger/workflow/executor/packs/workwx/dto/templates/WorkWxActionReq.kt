package com.github.wenslo.forger.workflow.executor.packs.workwx.dto.templates

import com.github.wenslo.forger.workflow.annotations.ActionRequest
import com.github.wenslo.forger.workflow.annotations.FieldDefine
import com.github.wenslo.forger.workflow.enums.ExecutorType
import com.github.wenslo.forger.workflow.enums.TemplateType

/**
 * @author wenhailin
 * @date 2022/11/27 14:31
 */
@ActionRequest(templateType = TemplateType.WORK_WEIXIN, executorType = ExecutorType.WORK_WEIXIN)
data class WorkWxActionReq(
    @FieldDefine(
        describe = "user_id",
        sortNum = 1
    )
    var userIdList: List<String> = emptyList(),
    @FieldDefine(
        describe = "content",
        len = 200,
        sortNum = 2
    )
    var content: String = "",
)