package com.github.wenslo.forger.workflow.service

import com.github.wenslo.forger.data.jpa.service.LongIdService
import com.github.wenslo.forger.workflow.condition.PlayScriptCondition
import com.github.wenslo.forger.workflow.entity.jpa.PlayScript
import com.github.wenslo.forger.workflow.entity.jpa.PlayScriptAction
import com.github.wenslo.forger.workflow.entity.jpa.PlayScriptActionShuttle

/**
 * @author wenhailin
 * @date 2022/11/3 09:25
 */
interface PlayScriptService : LongIdService<PlayScript, PlayScriptCondition> {
    fun savePlayScript(playScript: PlayScript)

    fun savePlayScriptNodeLine(playScript: PlayScript)

    fun savePlayScriptNode(playScript: PlayScript)

    fun savePlayScriptAction(playScript: PlayScript)

    fun saveParamShuttles(playScript: PlayScript)

    fun savePlayScriptParams(playScript: PlayScript)

    fun actionMapByUniqueId(playScriptId: Long): Map<String, PlayScriptAction>

    fun actionPreviousMap(playScriptId: Long): Map<String, List<String>>

    fun actionNextMap(playScriptId: Long): Map<String, List<String>>

    fun findNextEmpty(playScriptId: Long): List<String>

    fun saveTemplateParams(playScript: PlayScript)

    fun findShuttleByPreviousActoin(playScriptId: Long, actionUniqueId: String): List<PlayScriptActionShuttle>
}