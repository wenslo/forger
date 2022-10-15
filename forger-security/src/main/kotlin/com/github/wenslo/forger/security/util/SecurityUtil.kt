package com.github.wenslo.forger.security.util

import com.github.wenslo.forger.security.token.ApiAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails

object SecurityUtil {
    /**
     * get current online user detail, contains ip address
     */
    val loginUser: UserDetails
        get() = SecurityContextHolder.getContext().authentication?.details as UserDetails

    /**
     * get login token
     */
    val loginToken: ApiAuthenticationToken
        get() = (SecurityContextHolder.getContext().authentication as ApiAuthenticationToken?)
            ?: ApiAuthenticationToken("", "")
}
