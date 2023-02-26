package com.github.wenslo.forger.workflow.repository.jpa

import com.github.wenslo.forger.data.jpa.repository.LongIdRepository
import com.github.wenslo.forger.workflow.entity.jpa.PlayScriptExecuteRecordLog
import org.springframework.stereotype.Repository

/**
 * @author wenhailin
 * @date 2022/10/30 11:04
 */
@Repository
interface PlayScriptExecuteRecordLogRepository : LongIdRepository<PlayScriptExecuteRecordLog, Long> {
    fun findByPlayScriptId(playScriptId: Long): List<PlayScriptExecuteRecordLog>
}