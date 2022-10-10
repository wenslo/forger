package com.github.wenslo.forger.data.jpa.repository

import com.github.wenslo.forger.data.jpa.model.StringIdEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.data.repository.NoRepositoryBean
import java.io.Serializable
import java.util.*

@NoRepositoryBean
interface StringIdRepository<T : StringIdEntity, ID : Serializable> : JpaRepository<T, ID>,
    QuerydslPredicateExecutor<T> {
    fun findByIdIn(ids: List<ID>): List<T>?

    fun findByCreatedAtAfter(createdAtStart: Date?): List<T>?

    fun findByCreatedAtBefore(createdAtEnd: Date?): List<T>?

    fun findByUpdatedAtAfter(updatedAtStart: Date?): List<T>?

    fun findByUpdatedAtBefore(updatedAtEnd: Date?): List<T>?

    @Modifying
    fun deleteByIdIn(ids: List<ID>);
}