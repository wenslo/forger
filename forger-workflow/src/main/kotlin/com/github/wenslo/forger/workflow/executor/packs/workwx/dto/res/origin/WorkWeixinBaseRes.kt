package com.github.wenslo.forger.workflow.executor.packs.workwx.dto.res.origin

import com.google.gson.annotations.SerializedName

/**
 * @author wenhailin
 * @date 2022/11/11 09:53
 */
open class WorkWeixinBaseRes(
    @SerializedName("errcode")
    var errCode: Int? = null,
    @SerializedName("errmsg")
    val errMsg: String? = null
)