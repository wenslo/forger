package com.github.wenslo.forger.workflow.service.impl

import com.github.wenslo.forger.core.inline.getLogger
import com.github.wenslo.forger.workflow.entity.PlayScriptExecuteRecord
import com.github.wenslo.forger.workflow.exceptions.ExecuteException
import com.github.wenslo.forger.workflow.service.ActionProducerService
import com.google.gson.Gson
import org.apache.activemq.ScheduledMessage
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jms.core.JmsTemplate
import org.springframework.stereotype.Service
import java.util.*
import javax.jms.Destination
import javax.jms.Message
import javax.jms.Session

/**
 * @author wenhailin
 * @date 2022/10/30 00:02
 */
@Service
class ActionProducerServiceImpl : ActionProducerService {
    val logger = getLogger<ActionProducerServiceImpl>()

    companion object {
        private const val EXECUTE_QUEUE = "workflow_executor"
        private const val EXECUTE_PULL_QUEUE = "workflow_pull"
        private const val INSTANCE_ID_NAME = "instanceId"
        private const val EXECUTE_RECORD_ID_NAME = "executeRecordId"
        private const val EXECUTE_RECORD_LOG_ID_NAME = "executeRecordLogId"

    }

    @Autowired
    lateinit var gson: Gson

    @Autowired
    lateinit var jmsTemplate: JmsTemplate

    override fun sendNow(record: PlayScriptExecuteRecord) {
        logger.info("Sending text to MQ now, message payload is {}", gson.toJson(record))
        jmsTemplate.send(EXECUTE_QUEUE) { session: Session ->
            session.createTextMessage().apply {
                this.setStringProperty(INSTANCE_ID_NAME, record.playScriptId.toString())
                this.setStringProperty(EXECUTE_RECORD_ID_NAME, record.playScriptId.toString())
                this.setStringProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, "3000")
                this.text = gson.toJson(record)
            }
        }
    }

    override fun sendFixedTime(record: PlayScriptExecuteRecord, executeTime: Date) {
        logger.info(
            "Sending text to MQ at fixed time, execute time is {}, message payload is {}",
            executeTime,
            gson.toJson(record)
        )
        val delayMills = executeTime.time - System.currentTimeMillis()
        if (delayMills < 0) {
            throw ExecuteException("Execute time must more then current time")
        }
        jmsTemplate.send(EXECUTE_QUEUE) { session: Session ->
            session.createTextMessage().apply {
                this.setStringProperty(INSTANCE_ID_NAME, record.playScriptId.toString())
                this.setStringProperty(EXECUTE_RECORD_ID_NAME, record.playScriptId.toString())
                this.setStringProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delayMills.toString())
                this.text = gson.toJson(record)
            }
        }
    }

    override fun sendInterval(record: PlayScriptExecuteRecord, endTime: Date, cron: String) {
        logger.info(
            "Sending text to MQ interval, end time is {},cron express is {},  message payload is {}",
            endTime, cron, gson.toJson(record)
        )
        jmsTemplate.send(EXECUTE_QUEUE) { session: Session ->
            session.createTextMessage().apply {
                this.setStringProperty(INSTANCE_ID_NAME, record.playScriptId.toString())
                this.setStringProperty(ScheduledMessage.AMQ_SCHEDULED_CRON, cron)
                this.setStringProperty(ScheduledMessage.AMQ_SCHEDULER_ACTION_END_TIME, endTime.time.toString())
                this.text = gson.toJson(record)
            }
        }
    }

    override fun remove(id: Int, propertyName: String) {
        this.removeBatch(listOf(id), propertyName)
    }

    @Suppress("UNCHECKED_CAST")
    override fun removeBatch(idList: List<Int>, propertyName: String) {
        val factory = jmsTemplate.connectionFactory ?: return
        val conn = factory.createConnection()
        conn.start()
        val session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE)
        val requestDest: Destination = session.createTopic(ScheduledMessage.AMQ_SCHEDULER_MANAGEMENT_DESTINATION)
        val replyDest = session.createTemporaryQueue()
        val producer = session.createProducer(requestDest)
        val request = session.createTextMessage()
        request.setStringProperty(ScheduledMessage.AMQ_SCHEDULER_ACTION, ScheduledMessage.AMQ_SCHEDULER_ACTION_BROWSE)
        request.jmsReplyTo = replyDest
        producer.send(request)

        val requestBrowser = session.createBrowser(replyDest)
        val enumer: Enumeration<Message> = requestBrowser.enumeration as Enumeration<Message>
        while (enumer.hasMoreElements()) {
            val curMsg = enumer.nextElement()
            val scheduleId = curMsg.getStringProperty(ScheduledMessage.AMQ_SCHEDULED_ID)
            logger.info("schedule id is {}", scheduleId)
            val id = curMsg.getStringProperty(propertyName)
            if (StringUtils.isBlank(id)) {
                continue
            }
            if (idList.contains(id.toInt())) {
                val remove: Message = this.getRemoveMsg(session, curMsg)
                producer.send(remove)
            }
        }
    }

    private fun getRemoveMsg(session: Session, message: Message): Message {
        val remove = session.createMessage()
        remove.setStringProperty(ScheduledMessage.AMQ_SCHEDULER_ACTION, ScheduledMessage.AMQ_SCHEDULER_ACTION_REMOVE)
        val scheduleID: String = message.getStringProperty(ScheduledMessage.AMQ_SCHEDULED_ID)
        remove.setStringProperty(ScheduledMessage.AMQ_SCHEDULED_ID, scheduleID)
        return remove
    }

    override fun sendPullInterval(recordLogId: Int, cron: String) {
        logger.info(
            "Sending text to MQ, interval to get ack, cron express is {},  message payload is {}",
            cron, recordLogId
        )
        jmsTemplate.send(EXECUTE_QUEUE) { session: Session ->
            session.createTextMessage().apply {
                this.setStringProperty(EXECUTE_RECORD_LOG_ID_NAME, recordLogId.toString())
                this.setStringProperty(ScheduledMessage.AMQ_SCHEDULED_CRON, cron)
                this.text = recordLogId.toString()
            }
        }
    }
}