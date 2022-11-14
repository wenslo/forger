package com.github.wenslo.forger.workflow.executor.packs.workwx.dto.templates

import com.github.wenslo.forger.workflow.annotations.FieldDefine
import com.github.wenslo.forger.workflow.annotations.TemplateUse
import com.github.wenslo.forger.workflow.enums.TemplateType

/**
 * @author wenhailin
 * @date 2022/11/14 09:51
 */
@TemplateUse(TemplateType.WORK_WEIXIN)
data class WorkWeixinTemplateDto(
    @FieldDefine(
        describe = "crop_id",
        len = 500,
        sortNum = 1
    )
    var cropId: String = "",
    @FieldDefine(
        describe = "agent_id",
        sortNum = 2
    )
    var agentId: Int = -1,
    @FieldDefine(
        describe = "app_secret",
        sortNum = 3,
        len = 200
    )
    var appSecret: String = "",
    @FieldDefine(
        describe = "token",
        sortNum = 4,
        len = 200
    )
    var token: String = "",
    @FieldDefine(
        describe = "encoding_aes_key",
        sortNum = 5,
        len = 200
    )
    var encodingAesKey: String = ""
)