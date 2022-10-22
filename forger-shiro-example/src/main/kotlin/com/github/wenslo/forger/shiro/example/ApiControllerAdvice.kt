package com.github.wenslo.forger.shiro.example

import com.github.wenslo.forger.core.domain.Response
import com.github.wenslo.forger.core.inline.getLogger
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ApiControllerAdvice {
    val logger = getLogger<ApiControllerAdvice>()

    @ExceptionHandler(value = [Throwable::class])
    fun constraintViolationExceptionHandler(t: Throwable): Response {
        logger.error("Exception catching", t)
        return Response.SERVER_ERROR;
    }
}