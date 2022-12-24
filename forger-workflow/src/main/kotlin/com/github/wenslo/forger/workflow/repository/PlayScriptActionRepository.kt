package com.github.wenslo.forger.workflow.repository

import com.github.wenslo.forger.data.jpa.repository.LongIdRepository
import com.github.wenslo.forger.workflow.entity.PlayScriptAction
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

/**
 * @author wenhailin
 * @date 2022/10/30 11:04
 */
@Repository
interface PlayScriptActionRepository : LongIdRepository<PlayScriptAction, Long> {
    /**
     * find previous is empty
     */
    @Query("from PlayScriptAction where previous = '' and  playScriptId = ?1")
    fun findByPreviousEmptyAndPlayScriptId(playScriptId: Long): List<PlayScriptAction>

    @Query("from PlayScriptAction where next = '' and  playScriptId = ?1")
    fun findByNextEmptyAndPlayScriptId(playScriptId: Long): List<PlayScriptAction>

    fun findTopByPlayScriptIdAndUniqueId(playScriptId: Long, uniqueId: String): PlayScriptAction?

    fun findByPlayScriptId(playScriptId: Long): List<PlayScriptAction>
}