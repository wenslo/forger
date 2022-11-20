package com.github.wenslo.forger.workflow.repository

import com.github.wenslo.forger.data.jpa.repository.LongIdRepository
import com.github.wenslo.forger.workflow.entity.PlayScriptAction
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
    fun findByPreviousEmptyAndPlayScriptId(emptyFlag: Boolean, playScriptId: Long): List<PlayScriptAction>

    fun findByNextEmptyAndPlayScriptId(emptyFlag: Boolean, playScriptId: Long): List<PlayScriptAction>

    fun findTopByPlayScriptIdAndUniqueId(playScriptId: Long, uniqueId: String): PlayScriptAction?

    fun findByPlayScriptId(playScriptId: Long): List<PlayScriptAction>
}