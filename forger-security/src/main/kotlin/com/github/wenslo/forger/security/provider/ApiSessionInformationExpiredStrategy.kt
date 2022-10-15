package com.github.wenslo.forger.security.provider

import com.github.wenslo.forger.core.domain.Response
import com.github.wenslo.forger.core.util.GsonSingleton
import com.google.gson.Gson
import org.springframework.security.web.session.SessionInformationExpiredEvent
import org.springframework.security.web.session.SessionInformationExpiredStrategy
import org.springframework.stereotype.Component

@Component
class ApiSessionInformationExpiredStrategy : SessionInformationExpiredStrategy {
    var gson: Gson = GsonSingleton.getInstance()

    override fun onExpiredSessionDetected(event: SessionInformationExpiredEvent?) {
        val response = event!!.response
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"
        val writer = response.writer
        writer.append(gson.toJson(Response.UNAUTHORIZED))
        writer.flush()
        writer.close()
    }

}
