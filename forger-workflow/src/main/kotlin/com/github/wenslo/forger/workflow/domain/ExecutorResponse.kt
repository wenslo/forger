package com.github.wenslo.forger.workflow.domain

data class ExecutorResponse(
    var code: Int = 1,
    var message: String = "",
    var translatedData: Any? = null,
    var originData: Any? = null
)