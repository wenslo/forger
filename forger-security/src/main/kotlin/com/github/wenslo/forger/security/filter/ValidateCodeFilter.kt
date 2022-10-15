package com.github.wenslo.forger.security.filter

import cn.hutool.cache.CacheUtil
import cn.hutool.cache.impl.TimedCache
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.wenslo.forger.core.inline.getLogger
import com.github.wenslo.forger.security.model.AuthenticationBean
import com.github.wenslo.forger.security.provider.ApiAuthenticationFailureHandler
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletRequestWrapper
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class ValidateCodeFilter : OncePerRequestFilter() {
    val log = getLogger<ValidateCodeFilter>()

    companion object {
        val timedMap: TimedCache<String, String?> = CacheUtil.newTimedCache<String, String?>(5 * 60 * 1000.toLong())

        @JvmStatic
        val halfHourTimedMap: TimedCache<String, Long?> =
            CacheUtil.newTimedCache<String, Long?>(30 * 60 * 1000.toLong())
    }

    @Autowired
    lateinit var apiAuthenticationFailureHandler: ApiAuthenticationFailureHandler

    @Autowired
    lateinit var mapper: ObjectMapper

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val wrappedRequest = RequestWrapper(request)
        if (Objects.equals("/login", wrappedRequest.requestURI) && StringUtils.equalsIgnoreCase(
                "post",
                wrappedRequest.method
            )
        ) {
            val validate = validate(wrappedRequest)
            if (validate != null) {
                apiAuthenticationFailureHandler.onAuthenticationFailure(
                    wrappedRequest,
                    response,
                    UsernameNotFoundException(validate)
                )
                return
            }
        }
        filterChain.doFilter(wrappedRequest, response)
    }

    fun validate(request: HttpServletRequest): String? {
        val wrapper = ServletRequestWrapper(request)
        val inputStream = wrapper.inputStream
        val authenticationBean = mapper.readValue(inputStream, AuthenticationBean::class.java)
        val verifyCode = authenticationBean.verify
        logger.debug("Session is ${request.session.id}, verifyCode is $verifyCode")
        if (verifyCode == null || verifyCode.trim() == "") {
            return "Captcha is not allow empty!"
        }
        val code = timedMap.get(request.session.id) ?: return "Captcha is expired!"
        if (verifyCode != code) {
            return "Captcha is wrong!"
        }
        timedMap.remove(request.session.id)
        return null
    }
}