package com.github.wenslo.forger.workflow.utils

import com.github.wenslo.forger.core.exceptions.BusinessException
import com.github.wenslo.forger.core.inline.getLogger
import org.apache.commons.io.IOUtils
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse
import org.apache.hc.core5.http.HttpStatus
import org.apache.hc.core5.http.io.entity.EntityUtils
import java.io.StringWriter

/**
 * @author wenhailin
 * @date 2022/12/8 13:57
 */
object HttpClientUtil {
    private val logger = getLogger<HttpClientUtil>()
    private fun checkSuccess(response: CloseableHttpResponse) {
        if (response.code != HttpStatus.SC_SUCCESS) {
            throw BusinessException("Error")
        }
    }

    fun getBodyFromResponse(response: CloseableHttpResponse): String {
        checkSuccess(response)
        // content to string and to bean
        val writer = StringWriter()
        IOUtils.copy(response.entity.content, writer, "UTF-8")
        EntityUtils.consume(response.entity)
        val respStr = writer.toString()
        if (logger.isTraceEnabled) {
            logger.trace("response is $respStr")
        }
        return respStr
    }
}