package com.github.wenslo.forger.shiro.core

interface UserDetails {

    fun getUsername(): String

    fun getPassword(): String?

    fun getNickName(): String

    fun getEmail(): String

    fun getPhone(): String

    fun isAccountNonExpired(): Boolean

    fun isAccountNonLocked(): Boolean

    fun isCredentialsNonExpired(): Boolean

    fun isEnabled(): Boolean

    fun getAuthorities(): Set<String>

    fun getRoleCodeSet(): Set<String>
}