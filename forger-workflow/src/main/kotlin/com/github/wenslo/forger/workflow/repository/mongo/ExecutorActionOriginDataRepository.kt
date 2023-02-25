package com.github.wenslo.forger.workflow.repository.mongo

import com.github.wenslo.forger.workflow.entity.mongo.ExecutorActionOriginData
import org.bson.types.ObjectId
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * @author wenhailin
 * @date 2022/12/11 10:53
 */
@Repository
interface ExecutorActionOriginDataRepository : CrudRepository<ExecutorActionOriginData, ObjectId> {
    fun findTopByPlayScriptIdAndRecordLogId(playScriptId: Long, recordLogId: Long): ExecutorActionOriginData?
}