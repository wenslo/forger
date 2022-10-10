package com.github.wenslo.forger.data.jpa.repository

import com.github.wenslo.forger.data.jpa.model.OrgIdEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.data.repository.NoRepositoryBean
import java.io.Serializable

@NoRepositoryBean
interface OrgIdRepository<T : OrgIdEntity, ID : Serializable> : JpaRepository<T, ID>, QuerydslPredicateExecutor<T>,
    GuidRepository<T, ID> {

    fun findByOrgId(orgId: Long): List<T>?

    fun findByOrgIdIn(orgIdList: List<Long>): List<T>?
}