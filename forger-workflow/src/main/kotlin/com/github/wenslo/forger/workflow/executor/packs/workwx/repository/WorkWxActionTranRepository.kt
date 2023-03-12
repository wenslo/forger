package com.github.wenslo.forger.workflow.executor.packs.workwx.repository

import com.github.wenslo.forger.workflow.executor.packs.workwx.entity.WorkWxActionTran
import org.bson.types.ObjectId
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * @author wenhailin
 * @date 2023/3/12 12:30
 */
@Repository
interface WorkWxActionTranRepository : CrudRepository<WorkWxActionTran, ObjectId> {
}
