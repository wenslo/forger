package com.github.wenslo.forger.workflow.entity

import com.github.wenslo.forger.data.jpa.model.LongIdEntity
import com.github.wenslo.forger.workflow.enums.ExecuteStatus
import com.github.wenslo.forger.workflow.enums.ExecutorType
import com.github.wenslo.forger.workflow.enums.IsFlag
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

/**
 * @author wenhailin
 * @date 2022/10/27 10:55
 */
@Entity
data class PlayScriptExecuteRecordLog(
    var playScriptId: Long = 0,
    var playScriptUniqueId: String = "",
    var recordId: Long = 0,
    var actionUniqueId: String = "",
    var executorType: ExecutorType = ExecutorType.NONE,
    var beginTime: LocalDateTime = LocalDateTime.now(),
    var endTime: LocalDateTime = LocalDateTime.now().minusYears(1),
    @Enumerated(EnumType.STRING)
    var finishFlag: IsFlag = IsFlag.NO,
    @Enumerated(EnumType.STRING)
    var executeFlag: IsFlag = IsFlag.NO,
    @Enumerated(EnumType.STRING)
    var status: ExecuteStatus = ExecuteStatus.SUCCEED,
    var message: String = "",
    @Enumerated(EnumType.STRING)
    var hasReportFile: IsFlag = IsFlag.NO,
    var link: String = ""
) : LongIdEntity()