package com.github.wenslo.forger.workflow.repository.mongo

import com.github.wenslo.forger.workflow.entity.mongo.ExecutorActionTranslatedData
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * @author wenhailin
 * @date 2022/12/11 10:53
 */
@Repository
interface ExecutorActionTranslatedDataRepository : CrudRepository<ExecutorActionTranslatedData, ObjectId> {

    fun findTopByPlayScriptIdAndRecordLogId(playScriptId: Long, recordLogId: Long): ExecutorActionTranslatedData?

    @Query("{playScriptId:?0,actionUniqueId:{\$in: ?1}}")
    fun findByPlayScriptIdAndActionUniqueIdIn(
        playScriptId: Long,
        actionUniqueId: List<String>
    ): List<ExecutorActionTranslatedData>

}