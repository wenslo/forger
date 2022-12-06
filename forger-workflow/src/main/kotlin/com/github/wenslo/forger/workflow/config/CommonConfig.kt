package com.github.wenslo.forger.workflow.config

import com.github.wenslo.forger.core.util.GsonSingleton
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author wenhailin
 * @date 2022/12/6 23:45
 */
@Configuration
class CommonConfig {

    @Bean
    fun gson() = GsonSingleton.getInstance()
}