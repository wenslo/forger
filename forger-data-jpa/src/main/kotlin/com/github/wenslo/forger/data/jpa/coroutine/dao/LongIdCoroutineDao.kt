package com.github.wenslo.forger.data.jpa.coroutine.dao

import com.github.wenslo.forger.data.jpa.coroutine.repository.LongIdCoroutineRepository
import com.github.wenslo.forger.data.jpa.model.LongIdEntity
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.Predicate
import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.io.Serializable
import java.util.*

open class LongIdCoroutineDao<T : LongIdEntity, ID : Serializable> {
    @Autowired
    lateinit var repository: LongIdCoroutineRepository<T, ID>

    fun findByIdIn(ids: List<ID>): Flow<T>? = repository.findAllById(ids)

    fun findByCreatedAtAfter(createdAtStart: Date?): Flow<T> {
        return repository.findByCreatedAtAfter(createdAtStart)
    }

    fun findByCreatedAtBefore(createdAtEnd: Date?): Flow<T> {
        return repository.findByCreatedAtBefore(createdAtEnd)
    }

    fun findByUpdatedAtAfter(updatedAtStart: Date?): Flow<T> {
        return repository.findByUpdatedAtAfter(updatedAtStart)
    }

    fun findByUpdatedAtBefore(updatedAtEnd: Date?): Flow<T>? {
        return repository.findByUpdatedAtBefore(updatedAtEnd)
    }

    suspend fun deleteByIdIn(ids: List<ID>) {
        return repository.deleteByIdIn(ids.listIterator())
    }

    suspend fun <S : T> save(entity: S): T {
        return repository.save(entity)
    }

    suspend fun deleteInBatch(entities: MutableIterable<T>) {
        return repository.deleteAll(entities)
    }

    fun findAll(): Flow<T> {
        return repository.findAll()
    }


    @Deprecated("This function is deprecated , don't use it !", ReplaceWith("jpaQuery.select(predicate).order()"))
    fun findAll(predicate: Predicate, vararg orders: OrderSpecifier<*>): MutableIterable<T> {
        return mutableListOf()
    }

    @Deprecated("This function is deprecated , don't use it !", ReplaceWith("jpaQuery.order()"))
    fun findAll(vararg orders: OrderSpecifier<*>): MutableIterable<T> {
        return mutableListOf()
    }

    fun findAll(predicate: Predicate, pageable: Pageable): Page<T> {
        return repository.findAll(predicate, pageable)
    }

    suspend fun deleteById(id: ID) {
        return repository.deleteById(id)
    }

    suspend fun deleteAll(entities: MutableIterable<T>) {
        return repository.deleteAll(entities)
    }

    suspend fun deleteAll() {
        return repository.deleteAll()
    }

    fun <S : T> saveAll(entities: MutableIterable<S>): Flow<S> {
        return repository.saveAll(entities)
    }


    suspend fun count(): Long {
        return repository.count()
    }

    suspend fun getOne(id: ID): T? {
        return repository.findById(id)
    }

    fun findAllById(ids: MutableIterable<ID>): Flow<T> {
        return repository.findAllById(ids)
    }

    suspend fun existsById(id: ID): Boolean {
        return repository.existsById(id)
    }

    suspend fun exists(predicate: Predicate): Boolean {
        return repository.exists(predicate)
    }

    suspend fun delete(entity: T) {
        return repository.delete(entity)
    }

}