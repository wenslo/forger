package com.github.wenslo.forger.workflow.executor.packs.workwx.dto

import com.google.gson.annotations.SerializedName

/**
 * @author wenhailin
 * @date 2022/11/11 09:53
 */
open class WorkWxBaseRes(
    @SerializedName("errcode")
    var errCode: Int? = null,
    @SerializedName("errmsg")
    val errMsg: String? = null
)