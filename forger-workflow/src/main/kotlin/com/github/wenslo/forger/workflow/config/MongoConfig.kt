package com.github.wenslo.forger.workflow.config

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.BeanCreationException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

/**
 * @author wenhailin
 * @date 2022/12/24 13:29
 */
@Configuration
class MongoConfig {
    @Autowired
    lateinit var environment: Environment

    @Bean
    fun mongoClient(): MongoDatabase {
        val host = environment.getProperty("spring.data.mongodb.host") ?: ""
        val port = environment.getProperty("spring.data.mongodb.port") ?: ""
        val database = environment.getProperty("spring.data.mongodb.database") ?: ""
        if (StringUtils.isBlank(host) || StringUtils.isBlank(port)) {
            throw BeanCreationException("spring.data.mongodb host or port is null!")
        }
        val uri = "mongodb://$host:$port/?maxPoolSize=32"
        val settings = MongoClientSettings.builder()
            .applyConnectionString(ConnectionString(uri))
            .build()
        val mongoClient = MongoClients.create(settings)
        if (StringUtils.isBlank(database)) {
            throw BeanCreationException("spring.data.mongodb database is null!")
        }
        return mongoClient.getDatabase(database)
    }
}