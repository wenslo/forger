package com.github.wenslo.forger.workflow.engine

import javax.jms.Session
import javax.jms.TextMessage

/**
 * @author wenhailin
 * @date 2022/10/29 23:51
 */
interface ActionConsumerService {

    fun receiveExecuteMessageFromQueue(textMessage: TextMessage, session: Session)

    fun receivePullMessageFromQueue(textMessage: TextMessage, session: Session)

}