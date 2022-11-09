package com.github.wenslo.forger.workflow.executor.packs.workwx

import com.github.wenslo.forger.workflow.domain.ActionDto
import com.github.wenslo.forger.workflow.domain.ExecutorResponse
import com.github.wenslo.forger.workflow.enums.ActionType
import com.github.wenslo.forger.workflow.enums.IsFlag
import com.github.wenslo.forger.workflow.executor.BaseExecutor
import org.springframework.stereotype.Component
import java.io.File

/**
 * @author wenhailin
 * @date 2022/11/8 09:06
 */
@Component
class WorkWxExecutor : BaseExecutor() {

    override fun getResourceInfo(): ActionDto = ActionDto(
        name = "WorkWeixin", "V1.0", "Work Weixin", author = "Warren Wen",
        asyncFlag = IsFlag.NO, actionType = ActionType.NOTICE
    )


    override fun execute(any: Any): ExecutorResponse {
        //TODO get template dto
        //TODO get parameter dto
        //TODO return executed response
        TODO("Not yet implemented")
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