package com.github.wenslo.forger.shiro.example.module.auth.controller

import com.github.wenslo.forger.core.domain.Response
import com.github.wenslo.forger.core.inline.getLogger
import com.github.wenslo.forger.shiro.example.module.auth.entity.User
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.authc.UsernamePasswordToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {
    val logger = getLogger<UserController>()

    @PostMapping("login")
    fun login(@RequestBody user: User): Response {
        logger.info("Login beginning...")
        val subject = SecurityUtils.getSubject()
        if (!subject.isAuthenticated) {
            val token = UsernamePasswordToken(user.getUsername(), user.getPassword())
            try {
                subject.login(token)
            } catch (ae: AuthenticationException) {
                return Response.serverError(ae.message ?: "Login failed")
            }
        }
        return Response.complete("Login succeed")
    }

    @GetMapping("me")
    fun me(): Response {
        logger.info("Get current user info")
        val subject = SecurityUtils.getSubject()
        if (subject.principal != null) {
            return Response.success(subject.principal)
        }
        return Response.error("You must be login")
    }
}