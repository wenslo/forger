package com.github.wenslo.forger.workflow.executor.packs.workwx.dto

import com.google.gson.annotations.SerializedName

/**
 * @author wenhailin
 * @date 2022/11/11 09:55
 */
data class WorkWxToken(
    @SerializedName("access_token")
    var accessToken: String = "",
    @SerializedName("expires_in")
    var expiresIn: Int = 0,
) : WorkWxBaseRes()