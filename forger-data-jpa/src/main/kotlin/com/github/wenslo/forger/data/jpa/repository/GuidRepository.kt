package com.github.wenslo.forger.data.jpa.repository

import com.github.wenslo.forger.data.jpa.model.GuidEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.data.repository.NoRepositoryBean
import java.io.Serializable

@NoRepositoryBean
interface GuidRepository<T : GuidEntity, ID : Serializable> : JpaRepository<T, ID>, QuerydslPredicateExecutor<T>,
    LongIdRepository<T, ID> {

    fun findOneByGuid(guid: String): T?

    fun findByGuidIn(list: List<String>): List<T>?
}