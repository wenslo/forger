package com.github.wenslo.forger.workflow.repository.jpa

import com.github.wenslo.forger.data.jpa.repository.LongIdRepository
import com.github.wenslo.forger.workflow.entity.jpa.PlayScriptExecuteRecord
import com.github.wenslo.forger.workflow.enums.PlayScriptProcessStatus
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

/**
 * @author wenhailin
 * @date 2022/10/30 11:04
 */
@Repository
interface PlayScriptExecuteRecordRepository : LongIdRepository<PlayScriptExecuteRecord, Long> {
    @Modifying
    @Query("update PlayScriptExecuteRecord set processStatus = ?1 where id = ?2")
    fun updateProcessStatusById(status: PlayScriptProcessStatus, recordId: Long)
}