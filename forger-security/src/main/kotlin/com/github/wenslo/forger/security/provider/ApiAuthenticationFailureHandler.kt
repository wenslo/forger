package com.github.wenslo.forger.security.provider

import com.github.wenslo.forger.core.domain.Response
import com.github.wenslo.forger.core.inline.getLogger
import com.github.wenslo.forger.core.util.GsonSingleton
import com.google.gson.Gson
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class ApiAuthenticationFailureHandler : AuthenticationFailureHandler {
    val logger = getLogger<ApiAuthenticationFailureHandler>()

    var gson: Gson = GsonSingleton.getInstance()


    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"
        val writer = response.writer
        if (!exception.message.isNullOrBlank()) {
            writer.append(gson.toJson(Response.error(exception.message ?: "")))
        } else {
            writer.append(gson.toJson(Response.LOGIN_FAIL))
        }
        writer.flush()
        writer.close()
    }
}
