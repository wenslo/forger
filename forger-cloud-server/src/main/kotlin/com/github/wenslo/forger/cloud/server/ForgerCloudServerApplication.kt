package com.github.wenslo.forger.cloud.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.config.server.EnableConfigServer

/**
 * @author wenhailin
 * @date 2022/11/11 17:39
 */
@EnableConfigServer
@SpringBootApplication
class ForgerCloudServerApplication

fun main(args: Array<String>) {
    runApplication<ForgerCloudServerApplication>(*args)
}