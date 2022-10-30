package com.github.wenslo.forger.workflow.service.impl

import com.github.wenslo.forger.core.inline.getLogger
import com.github.wenslo.forger.workflow.entity.PlayScriptExecuteRecord
import com.github.wenslo.forger.workflow.service.ActionConsumerService
import com.github.wenslo.forger.workflow.service.PlayScriptStage
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Service
import javax.jms.Session
import javax.jms.TextMessage

/**
 * @author wenhailin
 * @date 2022/10/30 10:31
 */
@Service
class ActionConsumerServiceImpl : ActionConsumerService {

    val logger = getLogger<ActionConsumerServiceImpl>()

    @Autowired
    lateinit var gson: Gson

    @Autowired
    lateinit var playScriptStage: PlayScriptStage

    @JmsListener(destination = "executor")
    override fun receiveExecuteMessageFromQueue(textMessage: TextMessage, session: Session) {
        try {
            val record = gson.fromJson(textMessage.text, PlayScriptExecuteRecord::class.java)
            playScriptStage.execute(record)
            textMessage.acknowledge()
        } catch (e: Exception) {
            session.recover()
        }
    }

    @JmsListener(destination = "pull_check")
    override fun receivePullMessageFromQueue(textMessage: TextMessage, session: Session) {
        try {
            playScriptStage.getExecuteResult(textMessage.text.toInt())
            textMessage.acknowledge()
        } catch (e: Exception) {
            session.recover()
        }
    }
}