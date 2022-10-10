package com.github.wenslo.forger.data.jpa.repository

import com.github.wenslo.forger.data.jpa.model.NoArgEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.data.repository.NoRepositoryBean
import java.io.Serializable

@NoRepositoryBean
interface NoArgRepository<T : NoArgEntity, ID : Serializable> : JpaRepository<T, ID>, QuerydslPredicateExecutor<T> {
    fun findByIdIn(ids: List<ID>): List<T>?

    @Modifying
    fun deleteByIdIn(ids: List<ID>)
}