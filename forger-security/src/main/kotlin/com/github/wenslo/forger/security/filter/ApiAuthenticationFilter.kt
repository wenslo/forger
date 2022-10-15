package com.github.wenslo.forger.security.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.wenslo.forger.core.inline.getLogger
import com.github.wenslo.forger.security.model.AuthenticationBean
import com.github.wenslo.forger.security.token.ApiAuthenticationToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ApiAuthenticationFilter : UsernamePasswordAuthenticationFilter() {
    private val log = getLogger<ApiAuthenticationFilter>()

    @Autowired
    lateinit var mapper: ObjectMapper

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): org.springframework.security.core.Authentication {

        if (request.method != "POST") {
            throw AuthenticationServiceException("Unsupported request method")
        }
        return if (MediaType.APPLICATION_JSON_VALUE == request.contentType) {
            lateinit var authRequest: ApiAuthenticationToken
            try {
                request.inputStream.use { `is` ->
                    val authenticationBean: AuthenticationBean = mapper.readValue(`is`, AuthenticationBean::class.java)
                    authRequest = ApiAuthenticationToken(
                        authenticationBean.username, authenticationBean.password
                    )
                    authRequest.verify = authenticationBean.verify ?: ""
                }
            } catch (e: IOException) {
                log.error("obtain username is error", e)
                authRequest = ApiAuthenticationToken(
                    "", ""
                )
            }
            setDetails(request, authRequest)
            this.authenticationManager.authenticate(authRequest)
        } else {
            throw AuthenticationServiceException(HttpStatus.UNSUPPORTED_MEDIA_TYPE.reasonPhrase)
        }
    }
}
