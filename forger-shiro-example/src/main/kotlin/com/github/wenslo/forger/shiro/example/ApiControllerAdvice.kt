package com.github.wenslo.forger.shiro.example

import com.github.wenslo.forger.core.domain.Response
import com.github.wenslo.forger.core.inline.getLogger
import org.apache.shiro.authz.UnauthorizedException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController

@ControllerAdvice
@RestController
class ApiControllerAdvice {
    val logger = getLogger<ApiControllerAdvice>()

    @ExceptionHandler(value = [Throwable::class])
    fun constraintViolationExceptionHandler(t: Throwable): Response {
        logger.error("Exception catching", t)
        if (t is UnauthorizedException) {
            return Response.UNAUTHORIZED
        }
        return Response.SERVER_ERROR
    }
}