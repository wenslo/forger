package com.github.wenslo.forger.workflow.executor.packs.workwx.entity

import com.github.wenslo.forger.workflow.executor.packs.common.ActionIdEntity
import com.github.wenslo.forger.workflow.executor.packs.workwx.dto.WorkWxUserInfo
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId

/**
 * @author wenhailin
 * @date 2023/3/12 00:13
 */
@Document
data class WorkWxActionTran(
    @MongoId
    var id: ObjectId? = null,
    var userList: List<WorkWxUserInfo> = emptyList(),
    var content: String? = ""
) : ActionIdEntity()