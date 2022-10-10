package com.github.wenslo.forger.data.jpa.dao

import com.github.wenslo.forger.data.jpa.model.DeptIdEntity
import com.github.wenslo.forger.data.jpa.repository.DeptIdRepository
import org.springframework.beans.factory.annotation.Autowired
import java.io.Serializable

open class DeptIdDao<T : DeptIdEntity, ID : Serializable, R : DeptIdRepository<T, ID>> : OrgIdDao<T, ID, R>() {
    @Autowired
    override lateinit var repository: R

    fun findByDeptId(deptId: Long): List<T>? {
        return repository.findByDeptId(deptId)
    }

    fun findByDeptCode(deptCode: String): List<T>? {
        return repository.findByDeptCode(deptCode)
    }

    fun startWithByDeptCode(deptCode: String): List<T>? {
        return repository.findByDeptCode(deptCode)
    }
}