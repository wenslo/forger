package com.github.wenslo.forger.shiro.realms

import com.github.wenslo.forger.shiro.core.UserDetails
import com.github.wenslo.forger.shiro.service.UserDetailsService
import org.apache.shiro.authc.AuthenticationInfo
import org.apache.shiro.authc.AuthenticationToken
import org.apache.shiro.authc.SimpleAuthenticationInfo
import org.apache.shiro.authz.AuthorizationInfo
import org.apache.shiro.authz.SimpleAuthorizationInfo
import org.apache.shiro.realm.AuthorizingRealm
import org.apache.shiro.subject.PrincipalCollection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Component
class ApiRealm : AuthorizingRealm() {
    @Autowired
    lateinit var userDetailsService: UserDetailsService


    override fun doGetAuthorizationInfo(principals: PrincipalCollection): AuthorizationInfo {
        val user = principals.primaryPrincipal as UserDetails
        //TODO populate permission and role by username
        return SimpleAuthorizationInfo().apply {
            this.roles = user.getRoleCodeSet()
            this.stringPermissions = user.getAuthorities()
        }
    }

    override fun doGetAuthenticationInfo(token: AuthenticationToken): AuthenticationInfo {
        val username = token.principal.toString()
        val password = String((token.credentials as CharArray))
        val user = userDetailsService.loadByUsername(username)
            ?: throw RuntimeException("Username or password is error, try again later please")
        if (!user.isEnabled()) {
            throw RuntimeException("User is not enable")
        }
        if (!user.isAccountNonExpired()) {
            throw RuntimeException("User is expired")
        }
        if (!user.isAccountNonLocked()) {
            throw RuntimeException("User is locked")
        }
        if (!user.isCredentialsNonExpired()) {
            throw RuntimeException("User credential is expired")
        }
        return SimpleAuthenticationInfo(user, password, name)
    }

    override fun onLogout(principals: PrincipalCollection?) {
        super.onLogout(principals)
    }


}