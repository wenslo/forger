package com.github.wenslo.forger.workflow

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.github.wenslo.forger"])
class ForgerShiroExampleApplication

fun main(args: Array<String>) {
    runApplication<ForgerShiroExampleApplication>(*args)
}