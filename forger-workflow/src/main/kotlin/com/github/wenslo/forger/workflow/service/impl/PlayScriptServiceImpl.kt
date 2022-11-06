package com.github.wenslo.forger.workflow.service.impl

import com.github.wenslo.forger.data.jpa.service.LongIdServiceImpl
import com.github.wenslo.forger.workflow.condition.PlayScriptCondition
import com.github.wenslo.forger.workflow.entity.PlayScript
import com.github.wenslo.forger.workflow.repository.PlayScriptRepository
import com.github.wenslo.forger.workflow.service.PlayScriptService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author wenhailin
 * @date 2022/11/3 09:26
 */
@Service
@Transactional(readOnly = true)
class PlayScriptServiceImpl : PlayScriptService,
    LongIdServiceImpl<PlayScript, PlayScriptCondition, PlayScriptRepository>() {
    override fun savePlayScript() {
        TODO("Not yet implemented")
    }
}