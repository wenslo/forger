package com.github.wenslo.forger.workflow.executor

import com.github.wenslo.forger.workflow.domain.ActionDto
import com.github.wenslo.forger.workflow.domain.ExecuteShip
import com.github.wenslo.forger.workflow.domain.ExecutorResponse
import com.github.wenslo.forger.workflow.domain.FieldDto
import com.github.wenslo.forger.workflow.enums.ExecutorType
import com.github.wenslo.forger.workflow.repository.mongo.ActionLogBasicInfoRepository
import com.github.wenslo.forger.workflow.service.PlayScriptService
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
    lateinit var basicRepository: ActionLogBasicInfoRepository

    open fun getExecutorType(): ExecutorType {
        return this.getResourceInfo().executorType
    }

    open fun getTemplateDto(
        playScriptId: Long,
        executorType: ExecutorType,
        actionUniqueId: String
    ): List<FieldDto>? {
        //TODO not implement
        return emptyList()
    }

    open fun getActionParamDto(
        playScriptId: Long,
        executorType: ExecutorType,
        actionUniqueId: String
    ): List<FieldDto>? {
        //TODO not implement
        return emptyList()
        // find parameters from shuttle
//        val shuttles = playScriptService.findShuttleByPreviousActoin(playScriptId, actionUniqueId)
//        if (shuttles.isEmpty()) {
//            return actionParam
//        }
//
//        val previousActionIdList = shuttles.map { it.previousActionUniqueId }.distinct().toList()
//        // get result from action
//        val previousActionResults =
//            executorActionTranslatedDataRepository.findByPlayScriptIdAndActionUniqueIdIn(
//                playScriptId,
//                previousActionIdList
//            )
//        if (previousActionResults.isEmpty()) {
//            return actionParam
//        }
//        // find needs params
//        val previousFinishedActions =
//            previousActionResults.associateBy(keySelector = { it.actionUniqueId }, valueTransform = { it })
//        // TODO improve it
//        for (previous in previousActionIdList) {
//            val currents = previousFinishedActions[previous] ?: continue
//            val fieldDto = currents.params
//        }
//
//        return actionParam
    }

    abstract fun getResourceInfo(): ActionDto

    abstract fun execute(ship: ExecuteShip): ExecutorResponse

//    abstract fun getOriginData(playScriptId: Long, recordLogId: Long): ExecutorActionOriginData
//
//    abstract fun getTranslatedData(playScriptId: Long, recordLogId: Long): ExecutorActionTranslatedData


    abstract fun thresholdCheck()

    abstract fun getResultFile(): File
}