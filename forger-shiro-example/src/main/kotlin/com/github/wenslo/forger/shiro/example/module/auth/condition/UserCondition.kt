package com.github.wenslo.forger.shiro.example.module.auth.condition

import com.github.wenslo.forger.core.condition.LongIdCondition

data class UserCondition(
    var username: String = "",
    var nickname: String = "",
    var email: String = "",
    var phone: String = "",
    var enabled: Boolean? = null,
) : LongIdCondition()