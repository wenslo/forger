package com.github.wenslo.forger.workflow.domain

import com.github.wenslo.forger.workflow.enums.ExecuteStatus
import com.github.wenslo.forger.workflow.enums.IsFlag

data class ExecutorResponse(
    var status: ExecuteStatus = ExecuteStatus.SUCCEED,
    var message: String = "Succeed",
    var translatedData: Any? = null,
    var originData: Any? = null,
    var hasReportFile: IsFlag = IsFlag.NO,
    var link: String = ""
)