package com.github.wenslo.forger.workflow.service

import com.github.wenslo.forger.workflow.entity.PlayScriptExecuteRecord
import java.util.*

/**
 * @author wenhailin
 * @date 2022/10/29 23:50
 */
interface ActionProducerService {

    fun sendNow(record: PlayScriptExecuteRecord)

    fun sendFixedTime(record: PlayScriptExecuteRecord, executeTime: Date)

    fun sendInterval(record: PlayScriptExecuteRecord, endTime: Date, cron: String)

    fun remove(id: Int, propertyName: String)

    fun removeBatch(idList: List<Int>, propertyName: String)

    fun sendPullInterval(recordLogId: Int, cron: String)
}