package com.github.wenslo.forger.workflow.service.impl

import com.github.wenslo.forger.data.jpa.service.LongIdServiceImpl
import com.github.wenslo.forger.workflow.condition.PlayScriptExecuteRecordCondition
import com.github.wenslo.forger.workflow.domain.ExecuteShip
import com.github.wenslo.forger.workflow.entity.jpa.PlayScript
import com.github.wenslo.forger.workflow.entity.jpa.PlayScriptExecuteRecord
import com.github.wenslo.forger.workflow.enums.ExecuteStatus
import com.github.wenslo.forger.workflow.enums.IsFlag
import com.github.wenslo.forger.workflow.enums.PlayScriptProcessStatus
import com.github.wenslo.forger.workflow.repository.jpa.PlayScriptActionRepository
import com.github.wenslo.forger.workflow.repository.jpa.PlayScriptExecuteRecordLogRepository
import com.github.wenslo.forger.workflow.repository.jpa.PlayScriptExecuteRecordRepository
import com.github.wenslo.forger.workflow.service.PlayScriptExecuteRecordService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author wenhailin
 * @date 2022/11/4 15:42
 */
@Service
class PlayScriptExecuteRecordServiceImpl : PlayScriptExecuteRecordService,
    LongIdServiceImpl<PlayScriptExecuteRecord, PlayScriptExecuteRecordCondition, PlayScriptExecuteRecordRepository>() {
    @Autowired
    lateinit var recordRepository: PlayScriptExecuteRecordRepository

    @Autowired
    lateinit var actionRepository: PlayScriptActionRepository

    @Autowired
    lateinit var recordLogRepository: PlayScriptExecuteRecordLogRepository

    @Transactional
    override fun saveRecordAndGenerateShip(playScript: PlayScript): List<ExecuteShip> {
        val playScriptId = playScript.id!!
        val playScriptUniqueId = playScript.uniqueId

        val record = PlayScriptExecuteRecord()
        record.playScriptId = playScriptId
        record.playScriptUniqueId = playScriptUniqueId

        // find first be executing actions and then populate
        val actions = actionRepository.findByPreviousEmptyAndPlayScriptId(playScriptId)
        record.current = actions.map { it.uniqueId }.toList()
        this.recordRepository.save(record)

        val ships = record.current.map {
            ExecuteShip().apply {
                this.playScriptId = playScriptId
                this.playScriptUniqueId = playScriptUniqueId
                this.playScriptRecordId = record.id!!
                this.current = it
            }
        }.toList()
        return ships
    }

    @Transactional
    override fun finishById(status: PlayScriptProcessStatus, recordId: Long) {
        recordRepository.updateProcessStatusById(status, recordId)
    }

    override fun invokedStatusMapByPlayScriptId(playScriptId: Long): Map<String, IsFlag> {
        val list = recordLogRepository.findByPlayScriptId(playScriptId)
        if (list.isEmpty()) return emptyMap()
        return list.associateBy(
            keySelector = { it.actionUniqueId },
            valueTransform = { if (it.status == ExecuteStatus.SUCCEED) IsFlag.YES else IsFlag.NO })
    }
}