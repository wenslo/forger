package com.github.wenslo.forger.workflow.service

import com.github.wenslo.forger.data.jpa.service.LongIdService
import com.github.wenslo.forger.workflow.condition.PlayScriptCondition
import com.github.wenslo.forger.workflow.entity.PlayScript

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
}