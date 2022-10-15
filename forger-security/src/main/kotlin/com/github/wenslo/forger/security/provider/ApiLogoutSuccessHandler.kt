package com.github.wenslo.forger.security.provider

import com.github.wenslo.forger.core.domain.Response
import com.github.wenslo.forger.core.inline.getLogger
import com.github.wenslo.forger.core.util.GsonSingleton
import com.google.gson.Gson
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class ApiLogoutSuccessHandler : LogoutSuccessHandler {
    val logger = getLogger<ApiLogoutSuccessHandler>()

    var gson: Gson = GsonSingleton.getInstance()

    @Throws(IOException::class, ServletException::class)
    override fun onLogoutSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication?
    ) {
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"
        val writer = response.writer
        writer.append(gson.toJson(Response.LOGOUT_SUCCESS))
        writer.flush()
        writer.close()
    }
}
