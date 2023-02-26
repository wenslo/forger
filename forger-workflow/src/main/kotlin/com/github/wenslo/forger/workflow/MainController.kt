package com.github.wenslo.forger.workflow

import com.github.wenslo.forger.core.domain.Response
import com.github.wenslo.forger.core.inline.getLogger
import com.github.wenslo.forger.workflow.controller.ResourceController
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

/**
 * @author wenhailin
 * @date 2023/2/26 17:22
 */
@ControllerAdvice
@RestController
class MainController {
    companion object {
        private val logger = getLogger<ResourceController>()
    }

    @ExceptionHandler(Throwable::class)
    fun exceptionHandler(ex: Throwable, request: HttpServletRequest): Response? {
        logger.error("Request path is {}", request.requestURL)
        logger.error("Has wrong ...", ex)
        return Response.SERVER_ERROR
    }
}