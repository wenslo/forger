package com.github.wenslo.forger.workflow.executor.packs.workwx.dto.res.output

import com.github.wenslo.forger.workflow.annotations.ActionResponse
import com.github.wenslo.forger.workflow.enums.ExecutorType
import com.github.wenslo.forger.workflow.enums.TemplateType
import com.github.wenslo.forger.workflow.executor.packs.workwx.dto.req.WorkWxActionReq

/**
 * @author wenhailin
 * @date 2023/3/11 23:09
 */
@ActionResponse(templateType = TemplateType.WORK_WX, executorType = ExecutorType.WORK_WX_SEND)
class WorkWxActionOpRes() : WorkWxActionReq()