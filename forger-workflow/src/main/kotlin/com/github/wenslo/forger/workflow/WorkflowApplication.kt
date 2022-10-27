package com.github.wenslo.forger.workflow

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.github.wenslo.forger"])
@MapperScan("com.github.wenslo.forger.workflow.mapper")
class ForgerShiroExampleApplication

fun main(args: Array<String>) {
    runApplication<ForgerShiroExampleApplication>(*args)
}