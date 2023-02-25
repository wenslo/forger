package com.github.wenslo.forger.workflow.config

import com.fasterxml.jackson.databind.json.JsonMapper
import org.apache.activemq.ActiveMQConnectionFactory
import org.apache.activemq.RedeliveryPolicy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.jms.config.DefaultJmsListenerContainerFactory
import org.springframework.jms.config.JmsListenerContainerFactory
import org.springframework.jms.core.JmsTemplate
import org.springframework.jms.support.converter.MappingJackson2MessageConverter
import org.springframework.jms.support.converter.MessageConverter
import org.springframework.jms.support.converter.MessageType
import org.springframework.jms.support.destination.DynamicDestinationResolver
import javax.jms.Destination
import javax.jms.JMSException
import javax.jms.Session

/**
 * @author wenhailin
 * @date 2022/12/24 13:27
 */
@Configuration
class ActiveMQConfig {
    @Autowired
    lateinit var environment: Environment

    @Bean
    fun connectionFactory(): ActiveMQConnectionFactory? {
        val brokerUrl = environment.getProperty("spring.activemq.broker-url") ?: ""
        val brokerUsername = environment.getProperty("spring.activemq.user") ?: ""
        val brokerPassword = environment.getProperty("spring.activemq.password") ?: ""

        return ActiveMQConnectionFactory().apply {
            this.isTrustAllPackages = true
            this.brokerURL = brokerUrl
            this.password = brokerUsername
            this.userName = brokerPassword
            this.redeliveryPolicy = redeliveryPolicy()

        }
    }

    @Bean
    fun redeliveryPolicy(): RedeliveryPolicy? {
        return RedeliveryPolicy().apply {
            this.maximumRedeliveries = 1
            this.initialRedeliveryDelay = 10000
//            this.backOffMultiplier = 10.0
            this.isUseExponentialBackOff = true
        }
    }

    @Bean
    fun messageConverter(): MessageConverter? {
        return MappingJackson2MessageConverter().apply {
            this.setTargetType(MessageType.TEXT)
            this.setObjectMapper(JsonMapper())
        }
    }

    @Bean
    fun jmsTemplate(): JmsTemplate? {
        return JmsTemplate().apply {
            this.connectionFactory = connectionFactory()
            this.messageConverter = messageConverter()
            this.isPubSubDomain = true
            this.destinationResolver = destinationResolver()!!
            this.setDeliveryPersistent(true)
        }
    }

    @Bean
    fun destinationResolver(): DynamicDestinationResolver? {
        return object : DynamicDestinationResolver() {
            @Throws(JMSException::class)
            override fun resolveDestinationName(
                session: Session?,
                destinationName: String,
                pubSubDomain: Boolean
            ): Destination {
                var pubSubDomain = destinationName.endsWith("Topic")
                return super.resolveDestinationName(session, destinationName, pubSubDomain)
            }
        }
    }

    @Bean
    fun getJmsListenerContainerFactory(connectionFactory: ActiveMQConnectionFactory?): JmsListenerContainerFactory<*>? {
        return DefaultJmsListenerContainerFactory().apply {
            this.setConnectionFactory(connectionFactory!!)
            this.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE)
        }
    }
}