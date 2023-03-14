package com.github.wenslo.forger.workflow.executor

import com.github.wenslo.forger.core.exceptions.BusinessException
import com.github.wenslo.forger.workflow.domain.ActionDto
import com.github.wenslo.forger.workflow.domain.ExecuteShip
import com.github.wenslo.forger.workflow.domain.ExecutorResponse
import com.github.wenslo.forger.workflow.domain.FieldDto
import com.github.wenslo.forger.workflow.enums.ExecutorType
import com.github.wenslo.forger.workflow.enums.IsFlag
import com.github.wenslo.forger.workflow.repository.mongo.ActionBasicInfoRepository
import com.github.wenslo.forger.workflow.repository.mongo.ActionFieldsRepository
import com.github.wenslo.forger.workflow.repository.mongo.ActionLogBasicInfoRepository
import com.github.wenslo.forger.workflow.service.PlayScriptService
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import java.io.File

/**
 * @author wenhailin
 * @date 2022/10/26 09:09
 */
abstract class BaseExecutor {

    @Autowired
    lateinit var playScriptService: PlayScriptService

    @Autowired
    lateinit var basicLogRepository: ActionLogBasicInfoRepository

    @Autowired
    lateinit var basicInfoRepository: ActionBasicInfoRepository

    @Autowired
    lateinit var fieldsRepository: ActionFieldsRepository

    @Autowired
    lateinit var gson: Gson


    open fun getExecutorType(): ExecutorType {
        return this.getResourceInfo().executorType
    }

    fun getTemplateDto(
        playScriptId: Long,
        executorType: ExecutorType,
        actionUniqueId: String
    ): List<FieldDto>? {
        val basicInfo =
            basicInfoRepository.findTopByPlayScriptIdAndActionExecutorTypeAndActionUniqueIdAndTemplateFlag(
                playScriptId,
                executorType,
                actionUniqueId,
                IsFlag.YES
            ) ?: throw BusinessException("Template not installed")
        // find fields by basic object id
        return fieldsRepository.findByActionInfoId(basicInfo.id!!)
    }

    open fun getActionParamDto(
        playScriptId: Long,
        executorType: ExecutorType,
        actionUniqueId: String
    ): List<FieldDto>? {
        val basicInfo =
            basicInfoRepository.findTopByPlayScriptIdAndActionExecutorTypeAndActionUniqueIdAndTemplateFlag(
                playScriptId,
                executorType,
                actionUniqueId,
                IsFlag.NO
            ) ?: throw BusinessException("Action param has error")
        // find action fields by basic object id
        val fields = fieldsRepository.findByActionInfoId(basicInfo.id!!)

        // find parameters from shuttle
        val shuttles = playScriptService.findShuttleByPreviousActoin(playScriptId, actionUniqueId)
        if (shuttles.isEmpty()) {
            return fields
        }

        // get result from previous actions
        val opMap = mutableMapOf<String, Any?>()
        shuttles.map { it.previousActionUniqueId }.distinct().forEach { current ->
            this.getActionOp(playScriptId, current)?.let {
                opMap[current] = it
            }
        }
        if (opMap.isEmpty()) return fields

        val fieldMap = mutableMapOf<String, Any?>()
        // find needs params
        val previousShuttleMap = shuttles.groupBy { it.previousActionUniqueId }
        for (entry in previousShuttleMap.entries) {
            val previousActionId = entry.key
            val targets = entry.value
            val op = opMap[previousActionId]!!
            val objMap = gson.fromJson<HashMap<String, Any?>>(gson.toJson(op), Map::class.java)
            //get field value from action op, must loop targets and invoke
            for (target in targets) {
                // get value from target.previousActionFieldName of op
                val previousActionValue = objMap[target.previousActionFieldName]
                fieldMap[target.nextActionFieldName] = previousActionValue
            }
        }
        val res = mutableListOf<FieldDto>()
        res.addAll(fields)
        for (entry in fieldMap.entries) {
            res.add(FieldDto(name = entry.key, value = entry.value))
        }
        return res
    }

    abstract fun getResourceInfo(): ActionDto

    abstract fun execute(ship: ExecuteShip): ExecutorResponse

    abstract fun getActionOp(playScriptId: Long, actionUniqueId: String): Any?
//    abstract fun saveActionParam(
//        playScriptId: Long,
//        playScriptUniqueId: String,
//        actionUniqueId: String,
//        fields: List<FieldDto>
//    )
//
//    abstract fun saveTemplateParam(
//        playScriptId: Long,
//        playScriptUniqueId: String,
//        actionUniqueId: String,
//        fields: List<FieldDto>
//    )

//    abstract fun getOriginData(playScriptId: Long, recordLogId: Long): ExecutorActionOriginData
//
//    abstract fun getTranslatedData(playScriptId: Long, recordLogId: Long): ExecutorActionTranslatedData


    abstract fun thresholdCheck()

    abstract fun getResultFile(): File
}