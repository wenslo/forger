package com.github.wenslo.forger.workflow.executor.packs.workwx

import com.github.wenslo.forger.core.exceptions.BusinessException
import com.github.wenslo.forger.workflow.domain.ActionDto
import com.github.wenslo.forger.workflow.domain.ExecuteShip
import com.github.wenslo.forger.workflow.domain.ExecutorResponse
import com.github.wenslo.forger.workflow.enums.ActionType
import com.github.wenslo.forger.workflow.enums.ExecuteStatus
import com.github.wenslo.forger.workflow.enums.IsFlag
import com.github.wenslo.forger.workflow.executor.BaseExecutor
import com.github.wenslo.forger.workflow.executor.packs.workwx.dto.res.origin.WorkWeixinToken
import com.github.wenslo.forger.workflow.executor.packs.workwx.dto.templates.WorkWeixinActionReq
import com.github.wenslo.forger.workflow.executor.packs.workwx.dto.templates.WorkWeixinTemplateDto
import com.google.gson.Gson
import org.apache.commons.io.IOUtils
import org.apache.hc.client5.http.classic.methods.HttpGet
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient
import org.apache.hc.core5.http.HttpStatus
import org.apache.hc.core5.http.io.entity.EntityUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.File
import java.io.StringWriter

/**
 * @author wenhailin
 * @date 2022/11/8 09:06
 */
@Component
class WorkWxExecutor : BaseExecutor() {

    companion object {
        const val TOKEN_URL = "http://127.0.0.1:5000/gettoken"
    }

    @Autowired
    lateinit var httpClient: CloseableHttpClient
    @Autowired
    lateinit var gson:Gson

    override fun getResourceInfo(): ActionDto = ActionDto(
        name = "WorkWeixin", "V1.0", "Work Weixin", author = "Warren Wen",
        asyncFlag = IsFlag.NO, actionType = ActionType.NOTICE
    )


    override fun execute(ship: ExecuteShip): ExecutorResponse {
        // get template dto
        val templateParam =
            super.getTemplateDto(ship.playScriptId, super.getExecutorId(), ship.current)
                ?: return ExecutorResponse(
                    status = ExecuteStatus.PARAMS_NOT_EXISTS,
                    message = ExecuteStatus.PARAMS_NOT_EXISTS.name
                )

        // get parameter dto
        val actionParam = super.getActionParamDto(ship.playScriptId, super.getExecutorId(), ship.current)
            ?: return ExecutorResponse(
                status = ExecuteStatus.PARAMS_NOT_EXISTS,
                message = ExecuteStatus.PARAMS_NOT_EXISTS.name
            )
        val templateDto = templateParam.params as WorkWeixinTemplateDto
        val actionDto = actionParam.params as WorkWeixinActionReq
        val token = getWorkWeixinToken(templateDto)
        //return executed response
        return ExecutorResponse()
    }

    private fun getWorkWeixinToken(
        templateDto: WorkWeixinTemplateDto
    ): WorkWeixinToken {
        val requestUrl = TOKEN_URL + "?corpid=${templateDto.corpId}&corpsecret=${templateDto.appSecret}"
        val httpGet = HttpGet(requestUrl)
        val httpResponse = httpClient.execute(httpGet) ?: throw BusinessException("Server error")
        if (httpResponse.code != HttpStatus.SC_SUCCESS) {
            throw BusinessException("Error")
        }
        EntityUtils.consume(httpResponse.entity)
        // content to string and to bean
        val writer = StringWriter()
        IOUtils.copy(httpResponse.entity.content, writer, "UTF-8");
        return gson.fromJson(writer.toString(), WorkWeixinToken::class.java)
    }

    override fun getOriginData(): Any {
        TODO("Not yet implemented")
    }

    override fun getTranslatedData(): Any {
        TODO("Not yet implemented")
    }

    override fun getStoredOriginData(): Any {
        TODO("Not yet implemented")
    }

    override fun getStoredTranslatedData(): Any {
        TODO("Not yet implemented")
    }

    override fun thresholdCheck() {
        TODO("Not yet implemented")
    }

    override fun getResultFile(): File {
        TODO("Not yet implemented")
    }
}