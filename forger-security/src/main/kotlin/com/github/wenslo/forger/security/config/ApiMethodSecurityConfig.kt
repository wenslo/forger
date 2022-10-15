package com.github.wenslo.forger.security.config

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration

@EnableGlobalMethodSecurity(prePostEnabled = true)
class ApiMethodSecurityConfig : GlobalMethodSecurityConfiguration()
