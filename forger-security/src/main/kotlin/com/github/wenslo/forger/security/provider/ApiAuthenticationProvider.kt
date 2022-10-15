package com.github.wenslo.forger.security.provider

import com.github.wenslo.forger.core.domain.Response
import com.github.wenslo.forger.security.filter.ValidateCodeFilter.Companion.halfHourTimedMap
import com.github.wenslo.forger.security.token.ApiAuthenticationToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@Component
class ApiAuthenticationProvider : AuthenticationProvider {
    @Autowired
    lateinit var userDetailsService: UserDetailsService

    @Autowired
    lateinit var passwordEncoder: BCryptPasswordEncoder

    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication {
        val username = authentication.name
        val password = authentication.credentials.toString()
        val userDetails = userDetailsService.loadUserByUsername(username)
        val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?)?.request
        val key = request?.session?.id + username
        if (userDetails != null && passwordEncoder.matches(password, userDetails.password)) {
            if (!userDetails.isEnabled) {
                throw DisabledException(Response.DISABLES_MSG)
            }
            halfHourTimedMap.remove(key)
            return getAuthentication(username, password, userDetails)
        } else {
            var remainTimes = halfHourTimedMap.get(key) ?: 1
            if (remainTimes < 5L) {
                halfHourTimedMap.put(key, ++remainTimes)
                throw UsernameNotFoundException(Response.LOGIN_FAIL.msg)
            }
            if (remainTimes == 5L) {
                throw UsernameNotFoundException("User is locked, the duration time is 30 minutes, please try again later!")
            }
            throw UsernameNotFoundException(Response.LOGIN_FAIL.msg)
        }
    }

    fun getAuthentication(
        username: String?,
        password: String?,
        userDetails: UserDetails
    ): Authentication {
        val token =
            ApiAuthenticationToken(username, password, userDetails.authorities)
        token.details = userDetails
        //TODO if have more fields need be cached, put it on here
        return token
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return UsernamePasswordAuthenticationToken::class.java
            .isAssignableFrom(authentication)
    }
}
