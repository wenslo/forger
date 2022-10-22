package com.github.wenslo.forger.shiro.example.module.auth.entity

import com.baomidou.mybatisplus.annotation.TableField
import com.github.wenslo.forger.mp.model.LongIdEntity
import com.github.wenslo.forger.shiro.core.UserDetails

data class User(
    private var username: String = "",
    private var password: String = "",
    private var nickname: String = "",
    private var email: String = "",
    private var phone: String = "",
    @TableField(value = "account_non_expired")
    private var accountNonExpired: Boolean = false,
    @TableField(value = "account_non_locked")
    private var accountNonLocked: Boolean = false,
    @TableField(exist = false)
    private var credentialsNonExpired: Boolean = true,
    private var enabled: Boolean = false,
    @TableField(exist = false)
    private var authorities: Set<String> = setOf(),
    @TableField(exist = false)
    private var roleCodeSet: Set<String> = setOf(),
) : LongIdEntity(), UserDetails {


    override fun getUsername() = username

    override fun getPassword() = password

    override fun getNickName() = nickname

    override fun getEmail() = email

    override fun getPhone() = phone

    override fun isAccountNonExpired() = accountNonExpired

    override fun isAccountNonLocked() = accountNonLocked

    override fun isCredentialsNonExpired() = credentialsNonExpired

    override fun isEnabled() = enabled

    override fun getAuthorities() = authorities

    override fun getRoleCodeSet() = roleCodeSet
}