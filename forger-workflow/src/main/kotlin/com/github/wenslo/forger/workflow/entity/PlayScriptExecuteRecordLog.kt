package com.github.wenslo.forger.workflow.entity

import com.github.wenslo.forger.data.jpa.model.LongIdEntity
import com.github.wenslo.forger.workflow.enums.ExecuteStatus
import com.github.wenslo.forger.workflow.enums.IsFlag
import java.time.LocalDateTime

/**
 * @author wenhailin
 * @date 2022/10/27 10:55
 */
data class PlayScriptExecuteRecordLog(
    var playScriptId: Long = 0,
    var playScriptUniqueId: String = "",
    var recordId: Long = 0,
    var actionUniqueId: String = "",
    var executorId: String = "",
    var beginTime: LocalDateTime = LocalDateTime.now(),
    var endTime: LocalDateTime = LocalDateTime.now(),
    var executeFlag: IsFlag = IsFlag.NO,
    var status: ExecuteStatus = ExecuteStatus.SUCCEED,
    var message: String = "",
    var hasReportFile: IsFlag = IsFlag.NO,
    var link: String = ""
) : LongIdEntity()