package com.github.wenslo.forger.security.token

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class ApiAuthenticationToken : UsernamePasswordAuthenticationToken {
    constructor(principal: Any?, credentials: Any?) : super(principal, credentials)

    constructor(
        principal: Any?,
        credentials: Any?,
        authorities: Collection<GrantedAuthority?>?
    ) : super(principal, credentials, authorities)

    /** 图片验证码 **/
    var verify: String = ""

}
