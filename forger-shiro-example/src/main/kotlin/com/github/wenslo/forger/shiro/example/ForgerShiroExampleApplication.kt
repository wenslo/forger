package com.github.wenslo.forger.shiro.example

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.github.wenslo.forger"])
@MapperScan("com.github.wenslo.forger.shiro.example.**.mapper")
class ForgerShiroExampleApplication

fun main(args: Array<String>) {
    runApplication<ForgerShiroExampleApplication>(*args)
}