package com.github.wenslo.forger.data.jpa.dao

import com.github.wenslo.forger.data.jpa.model.OrgIdEntity
import com.github.wenslo.forger.data.jpa.repository.OrgIdRepository
import org.springframework.beans.factory.annotation.Autowired
import java.io.Serializable

abstract class OrgIdDao<T : OrgIdEntity, ID : Serializable, R : OrgIdRepository<T, ID>> : GuidDao<T, ID, R>() {
    @Autowired
    override lateinit var repository: R

    fun findByOrgId(orgId: Long): List<T>? {
        return repository.findByOrgId(orgId)
    }

    fun findByOrgIdIn(list: List<Long>): List<T>? {
        return repository.findByOrgIdIn(list)
    }
}