package com.github.wenslo.forger.shiro.service

import com.github.wenslo.forger.shiro.core.UserDetails

interface UserDetailsService {

    fun loadByUsername(username: String): UserDetails?
}