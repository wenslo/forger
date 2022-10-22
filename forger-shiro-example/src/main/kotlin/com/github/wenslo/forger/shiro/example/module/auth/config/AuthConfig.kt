package com.github.wenslo.forger.shiro.example.module.auth.config

import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AuthConfig {

    @Bean
    fun shiroFilterChainDefinition(): ShiroFilterChainDefinition {
        return DefaultShiroFilterChainDefinition().apply {
            this.addPathDefinition("/**", "anon")
        }
    }
}