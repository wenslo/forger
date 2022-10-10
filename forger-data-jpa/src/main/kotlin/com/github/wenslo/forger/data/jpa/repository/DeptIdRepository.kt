package com.github.wenslo.forger.data.jpa.repository

import com.github.wenslo.forger.data.jpa.model.DeptIdEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.data.repository.NoRepositoryBean
import java.io.Serializable

@NoRepositoryBean
interface DeptIdRepository<T : DeptIdEntity, ID : Serializable> : JpaRepository<T, ID>, QuerydslPredicateExecutor<T>,
    OrgIdRepository<T, ID> {

    fun findByDeptId(deptId: Long): List<T>?

    fun findByDeptCode(deptCode: String): List<T>?

    fun startWithByDeptCode(deptCode: String): List<T>?
}