package com.github.wenslo.forger.workflow.domain

/**
 * @author wenhailin
 * @date 2022/10/24 09:21
 */
data class ExecutorTemplateParam(
    var requestUrl: String = "",
    var username: String = "",
    var password: String = "",
    var token: String = "",
    var serverIpAddress: String = "",
    var serverUsername: String = "",
    var serverPassword: String = ""
)