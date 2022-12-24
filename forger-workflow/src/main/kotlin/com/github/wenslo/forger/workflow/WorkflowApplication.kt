package com.github.wenslo.forger.workflow

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication(scanBasePackages = ["com.github.wenslo.forger"])
@EntityScan("com.github.wenslo.forger.data.jpa.model", "com.github.wenslo.forger.workflow.entity")
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = ["com.github.wenslo.forger.data.jpa.repository", "com.github.wenslo.forger.workflow.repository"])
@EnableMongoRepositories
class ForgerWorkflowApplication

fun main(args: Array<String>) {
    runApplication<ForgerWorkflowApplication>(*args)
}