package com.github.wenslo.forger.workflow.executor.packs.workwx.define

import com.github.wenslo.forger.workflow.annotations.ActionRequest
import com.github.wenslo.forger.workflow.annotations.FieldDefine
import com.github.wenslo.forger.workflow.enums.ExecutorType
import com.github.wenslo.forger.workflow.enums.TemplateType
import com.github.wenslo.forger.workflow.executor.packs.common.ActionIdEntity
import com.github.wenslo.forger.workflow.executor.packs.common.ParamCacheable

/**
 * @author wenhailin
 * @date 2022/11/27 14:31
 */
@ActionRequest(templateType = TemplateType.WORK_WX, executorType = ExecutorType.WORK_WX_SEND)
open class WorkWxActionDto(
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
) : ActionIdEntity(), ParamCacheable