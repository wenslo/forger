package com.github.wenslo.forger.security.model

data class AuthenticationBean(
    var username: String? = null,
    var password: String? = null,
    var verify: String? = null
)
