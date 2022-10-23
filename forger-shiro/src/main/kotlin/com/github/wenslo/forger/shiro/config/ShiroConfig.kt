package com.github.wenslo.forger.shiro.config

import com.github.wenslo.forger.shiro.realms.ApiRealm
import org.apache.shiro.codec.Base64
import org.apache.shiro.mgt.RememberMeManager
import org.apache.shiro.session.mgt.DefaultSessionManager
import org.apache.shiro.session.mgt.eis.MemorySessionDAO
import org.apache.shiro.web.mgt.CookieRememberMeManager
import org.apache.shiro.web.mgt.DefaultWebSecurityManager
import org.apache.shiro.web.servlet.SimpleCookie
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class ShiroConfig {
    companion object {
        const val DEFAULT_GLOBAL_SESSION_TIMEOUT: Long = 24 * 60 * 60 * 1000
//        const val DEFAULT_GLOBAL_SESSION_TIMEOUT: Long = 5 * 1000

        const val REMEMBER_ME_KEY = "M1RIN2FVNGt6T2lRU2VNAA=="
    }


    @Bean
    fun securityManager(shiroRealm: ApiRealm, sessionManager: DefaultSessionManager): DefaultWebSecurityManager {
        return DefaultWebSecurityManager().apply {
            this.setRealm(shiroRealm)
            this.sessionManager = sessionManager
            this.rememberMeManager = rememberMeManager()
        }
    }

    @Bean
    fun sessionManager(): DefaultWebSessionManager {
        return DefaultWebSessionManager().apply {
            this.globalSessionTimeout = DEFAULT_GLOBAL_SESSION_TIMEOUT
            this.sessionDAO = MemorySessionDAO()
            this.isSessionIdUrlRewritingEnabled = false
        }
    }

    private fun rememberMeManager(): RememberMeManager {
        return CookieRememberMeManager().apply {
            this.cookie = rememberMeCookie()
            this.cipherKey = Base64.decode(REMEMBER_ME_KEY)
        }
    }

    private fun rememberMeCookie(): SimpleCookie? {
        return SimpleCookie("rememberMe").apply {
            this.maxAge = 60 * 60 * 24
        }
    }
}