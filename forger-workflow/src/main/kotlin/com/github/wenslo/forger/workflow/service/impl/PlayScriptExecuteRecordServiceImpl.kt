package com.github.wenslo.forger.workflow.service.impl

import com.github.wenslo.forger.workflow.domain.ExecuteShip
import com.github.wenslo.forger.workflow.entity.PlayScript
import com.github.wenslo.forger.workflow.entity.PlayScriptExecuteRecord
import com.github.wenslo.forger.workflow.repository.PlayScriptActionRepository
import com.github.wenslo.forger.workflow.repository.PlayScriptExecuteRecordRepository
import com.github.wenslo.forger.workflow.service.PlayScriptExecuteRecordService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author wenhailin
 * @date 2022/11/4 15:42
 */
@Service
class PlayScriptExecuteRecordServiceImpl : PlayScriptExecuteRecordService {
    @Autowired
    lateinit var recordRepository: PlayScriptExecuteRecordRepository

    @Autowired
    lateinit var actionRepository: PlayScriptActionRepository

    @Transactional
    override fun saveRecordAndGenerateShip(playScript: PlayScript): List<ExecuteShip> {
        val playScriptId = playScript.id!!
        val playScriptUniqueId = playScript.uniqueId

        val record = PlayScriptExecuteRecord()
        record.playScriptId = playScriptId
        record.playScriptUniqueId = playScriptUniqueId

        // find first be execute actions and then populate
        val actions = actionRepository.findByPreviousEmpty(true)
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
}