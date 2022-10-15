package com.github.wenslo.forger.security.provider

import com.github.wenslo.forger.core.domain.Response
import com.github.wenslo.forger.core.util.GsonSingleton
import com.google.gson.Gson
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class ApiAccessDeniedHandler : AccessDeniedHandler {
    var gson: Gson = GsonSingleton.getInstance()

    @Throws(IOException::class, ServletException::class)
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"
        val writer = response.writer
        writer.append(gson.toJson(Response.FORBIDDEN))
        writer.flush()
    }
}