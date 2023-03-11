package com.github.wenslo.forger.workflow.executor.packs.workwx

import com.github.wenslo.forger.core.exceptions.BusinessException
import com.github.wenslo.forger.core.inline.getLogger
import com.github.wenslo.forger.workflow.domain.ActionDto
import com.github.wenslo.forger.workflow.domain.ExecuteShip
import com.github.wenslo.forger.workflow.domain.ExecutorResponse
import com.github.wenslo.forger.workflow.entity.mongo.ExecutorActionOriginData
import com.github.wenslo.forger.workflow.entity.mongo.ExecutorActionTranslatedData
import com.github.wenslo.forger.workflow.enums.ActionType
import com.github.wenslo.forger.workflow.enums.ExecuteStatus
import com.github.wenslo.forger.workflow.enums.ExecutorType
import com.github.wenslo.forger.workflow.enums.IsFlag
import com.github.wenslo.forger.workflow.executor.BaseExecutor
import com.github.wenslo.forger.workflow.executor.packs.workwx.dto.req.WorkWxActionReq
import com.github.wenslo.forger.workflow.executor.packs.workwx.dto.res.origin.WorkWxBaseRes
import com.github.wenslo.forger.workflow.executor.packs.workwx.dto.res.origin.WorkWxToken
import com.github.wenslo.forger.workflow.executor.packs.workwx.dto.res.origin.WorkWxUser
import com.github.wenslo.forger.workflow.executor.packs.workwx.dto.res.origin.WorkWxUserInfo
import com.github.wenslo.forger.workflow.executor.packs.workwx.dto.templates.WorkWxTemplateDto
import com.github.wenslo.forger.workflow.repository.mongo.ExecutorActionOriginDataRepository
import com.github.wenslo.forger.workflow.utils.FieldDtoUtil
import com.github.wenslo.forger.workflow.utils.HttpClientUtil
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import org.apache.hc.client5.http.classic.methods.HttpGet
import org.apache.hc.client5.http.classic.methods.HttpPost
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient
import org.apache.hc.core5.http.io.entity.StringEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.File
import java.nio.charset.Charset

/**
 * @author wenhailin
 * @date 2022/11/8 09:06
 */
@Component
class WorkWxExecutor : BaseExecutor() {

    companion object {
        const val TOKEN_URL = "http://127.0.0.1:5000/gettoken"
        const val SEND_URL = "http://127.0.0.1:5000/send"
        const val GET_USER_URL = "http://127.0.0.1:5000/getuser"
        private val logger = getLogger<WorkWxExecutor>()
    }

    @Autowired
    lateinit var httpClient: CloseableHttpClient

    @Autowired
    lateinit var gson: Gson

    @Autowired
    lateinit var executorActionOriginDataRepository: ExecutorActionOriginDataRepository

    override fun getResourceInfo(): ActionDto = ActionDto(
        name = "WorkWeixin", "V1.0", "Work Weixin", author = "Warren Wen",
        asyncFlag = IsFlag.NO, actionType = ActionType.NOTICE, executorType = ExecutorType.WORK_WX_SEND
    )


    override fun execute(ship: ExecuteShip): ExecutorResponse {
        // get template dto
        val templateParam =
            super.getTemplateDto(ship.playScriptId, super.getExecutorType(), ship.current)
                ?: return ExecutorResponse(
                    status = ExecuteStatus.PARAMS_NOT_EXISTS,
                    message = ExecuteStatus.PARAMS_NOT_EXISTS.name
                )

        // get parameter dto
        val actionParam = super.getActionParamDto(ship.playScriptId, super.getExecutorType(), ship.current)
            ?: return ExecutorResponse(
                status = ExecuteStatus.PARAMS_NOT_EXISTS,
                message = ExecuteStatus.PARAMS_NOT_EXISTS.name
            )
        val templateDto = FieldDtoUtil.convert(templateParam.params, WorkWxTemplateDto::class.java)
        val actionDto = FieldDtoUtil.convert(actionParam.params, WorkWxActionReq::class.java)
        if (templateDto == null || actionDto == null) return ExecutorResponse(
            status = ExecuteStatus.ERROR,
            message = "error"
        )
        val token = getWorkWeixinToken(templateDto)
        val status = sendWorkWeixinInfo(token, templateDto, actionDto)
        val user = getWorkWeixinUser(token, actionDto)
        this.saveOriginData(ship, user)
        this.saveTranslatedData(ship, user)
        return if (status) {
            //return executed response
            ExecutorResponse(originData = actionDto, translatedData = actionDto)
        } else {
            ExecutorResponse(status = ExecuteStatus.ERROR, message = "error")
        }

    }

    private fun getWorkWeixinUser(
        token: WorkWxToken,
        actionDto: WorkWxActionReq,
    ): WorkWxUser {
        val userList = mutableListOf<WorkWxUserInfo>()
        actionDto.userIdList.forEach { userId ->
            val requestUrl = GET_USER_URL + "?access_token=${token.accessToken}&userid=${userId}"
            val httpGet = HttpGet(requestUrl)
            val httpResponse = httpClient.execute(httpGet) ?: throw BusinessException("Server error")
            val respStr = HttpClientUtil.getBodyFromResponse(httpResponse)
            val workWxUserInfo = gson.fromJson(respStr, WorkWxUserInfo::class.java)
            userList.add(workWxUserInfo)
        }
        return WorkWxUser(userList = userList, content = actionDto.content)
    }

    private fun saveTranslatedData(ship: ExecuteShip, user: WorkWxUser) {
        val executorActionTranslatedData = ExecutorActionTranslatedData().apply {
            this.playScriptId = ship.playScriptId
            this.playScriptUniqueId = ship.playScriptUniqueId
            this.actionUniqueId = ship.current
            this.actionExecutorType = super.getExecutorType()
            this.recordLogId = ship.recordLogId
            this.params = user
        }
        executorActionTranslatedDataRepository.save(executorActionTranslatedData)
    }

    private fun saveOriginData(ship: ExecuteShip, user: WorkWxUser) {
        val executorActionOriginData = ExecutorActionOriginData().apply {
            this.playScriptId = ship.playScriptId
            this.playScriptUniqueId = ship.playScriptUniqueId
            this.actionUniqueId = ship.current
            this.actionExecutorType = super.getExecutorType()
            this.recordLogId = ship.recordLogId
            this.params = user
        }
        executorActionOriginDataRepository.save(executorActionOriginData)
    }

    private fun sendWorkWeixinInfo(
        token: WorkWxToken,
        templateDto: WorkWxTemplateDto,
        actionDto: WorkWxActionReq
    ): Boolean {
        val weixinUserIdParam = actionDto.userIdList.joinToString("|")
        val param = object {
            @SerializedName("touser")
            val toUser = weixinUserIdParam

            @SerializedName("msgType")
            val msgType = "text"

            @SerializedName("agentid")
            val agentId = templateDto.agentId

            @SerializedName("text")
            val text = mapOf<String, String>(
                "content" to actionDto.content
            )

            @SerializedName("safe")
            val safe = 0

            @SerializedName("enable_id_trans")
            val enableIdTrans = 0

            @SerializedName("enable_duplicate_check")
            val enableDuplicateCheck = 0
        }
        val httpPost = HttpPost(SEND_URL + "？access_token=" + token.accessToken)
        httpPost.addHeader("content-type", "application/json")
        httpPost.entity = StringEntity(gson.toJson(param), Charset.forName("UTF-8"))
        val httpResponse = httpClient.execute(httpPost)
        val respStr = HttpClientUtil.getBodyFromResponse(httpResponse)
        val weixinBaseRes = gson.fromJson(respStr, WorkWxBaseRes::class.java)
        if (weixinBaseRes.errCode != 0) {
            throw BusinessException("Error")
        }
        return true
    }

    private fun getWorkWeixinToken(
        templateDto: WorkWxTemplateDto
    ): WorkWxToken {
        val requestUrl = TOKEN_URL + "?corpid=${templateDto.corpId}&corpsecret=${templateDto.appSecret}"
        val httpGet = HttpGet(requestUrl)
        val httpResponse = httpClient.execute(httpGet) ?: throw BusinessException("Server error")
        val respStr = HttpClientUtil.getBodyFromResponse(httpResponse)
        return gson.fromJson(respStr, WorkWxToken::class.java)
    }

    override fun getOriginData(playScriptId: Long, recordLogId: Long): ExecutorActionOriginData {
        return executorActionOriginDataRepository.findTopByPlayScriptIdAndRecordLogId(playScriptId, recordLogId)
            ?: ExecutorActionOriginData()
    }

    override fun getTranslatedData(playScriptId: Long, recordLogId: Long): ExecutorActionTranslatedData {
        return executorActionTranslatedDataRepository.findTopByPlayScriptIdAndRecordLogId(playScriptId, recordLogId)
            ?: ExecutorActionTranslatedData()
    }

    override fun thresholdCheck() {
        TODO("Not yet implemented")
    }

    override fun getResultFile(): File {
        TODO("Not yet implemented")
    }
}