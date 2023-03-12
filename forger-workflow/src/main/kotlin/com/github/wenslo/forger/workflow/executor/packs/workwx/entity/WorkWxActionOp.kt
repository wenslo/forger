package com.github.wenslo.forger.workflow.executor.packs.workwx.entity

import com.github.wenslo.forger.workflow.annotations.ActionResponse
import com.github.wenslo.forger.workflow.annotations.FieldDefine
import com.github.wenslo.forger.workflow.enums.ExecutorType
import com.github.wenslo.forger.workflow.enums.TemplateType
import com.github.wenslo.forger.workflow.executor.packs.common.ActionIdEntity
import com.github.wenslo.forger.workflow.executor.packs.common.ParamCacheable
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId

/**
 * @author wenhailin
 * @date 2022/11/27 14:31
 */
@Document("work_wx_action_op")
@ActionResponse(templateType = TemplateType.WORK_WX, executorType = ExecutorType.WORK_WX_SEND)
open class WorkWxActionOp(
    @MongoId
    var id: ObjectId? = null,
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