package com.github.wenslo.forger.data.jpa.dao

import com.github.wenslo.forger.data.jpa.model.GuidEntity
import com.github.wenslo.forger.data.jpa.repository.GuidRepository
import org.springframework.beans.factory.annotation.Autowired
import java.io.Serializable

abstract class GuidDao<T : GuidEntity, ID : Serializable, R : GuidRepository<T, ID>> : LongIdDao<T, ID, R>() {
    @Autowired
    override lateinit var repository: R

    protected open fun findOneByGuid(guid: String): T? {
        return repository.findOneByGuid(guid)
    }

    protected open fun findByGuidIn(list: List<String>): List<T>? {
        return repository.findByGuidIn(list)
    }

}