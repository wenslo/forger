package com.github.wenslo.forger.data.jpa.coroutine.repository

import com.github.wenslo.forger.data.jpa.model.LongIdEntity
import kotlinx.coroutines.flow.Flow
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.kotlin.CoroutineSortingRepository
import java.io.Serializable
import java.util.*

@NoRepositoryBean
interface LongIdCoroutineRepository<T : LongIdEntity, ID : Serializable> : CoroutineSortingRepository<T, ID>,
    QuerydslPredicateExecutor<T> {

    fun findByCreatedAtAfter(createdAtStart: Date?): Flow<T>

    fun findByCreatedAtBefore(createdAtEnd: Date?): Flow<T>

    fun findByUpdatedAtAfter(updatedAtStart: Date?): Flow<T>

    fun findByUpdatedAtBefore(updatedAtEnd: Date?): Flow<T>

    @Modifying
    suspend fun deleteByIdIn(ids: Iterator<ID>)
}