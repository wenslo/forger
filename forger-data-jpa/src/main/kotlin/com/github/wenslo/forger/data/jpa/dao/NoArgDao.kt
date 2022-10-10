package com.github.wenslo.forger.data.jpa.dao

import com.github.wenslo.forger.data.jpa.model.NoArgEntity
import com.github.wenslo.forger.data.jpa.repository.NoArgRepository
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.Predicate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import java.io.Serializable
import java.util.*

open class NoArgDao<T : NoArgEntity, ID : Serializable> {
    @Autowired
    lateinit var repository: NoArgRepository<T, ID>

    fun findByIdIn(ids: List<ID>): List<T>? = repository.findByIdIn(ids)

    fun deleteByIdIn(ids: List<ID>) {
        return repository.deleteByIdIn(ids)
    }

    fun <S : T> save(entity: S): S {
        return repository.save(entity)
    }

    fun deleteInBatch(entities: MutableIterable<T>) {
        return repository.deleteInBatch(entities)
    }

    fun findAll(): MutableList<T> {
        return repository.findAll()
    }

    fun findAll(sort: Sort): MutableList<T> {
        return repository.findAll(sort)
    }

    fun <S : T> findAll(example: Example<S>): MutableList<S> {
        return repository.findAll(example)
    }

    fun <S : T> findAll(example: Example<S>, sort: Sort): MutableList<S> {
        return repository.findAll(example, sort)
    }

    fun findAll(pageable: Pageable): Page<T> {
        return repository.findAll(pageable)
    }

    fun <S : T> findAll(example: Example<S>, pageable: Pageable): Page<S> {
        return repository.findAll(example, pageable)
    }

    fun findAll(predicate: Predicate): MutableIterable<T> {
        return repository.findAll(predicate)
    }

    fun findAll(predicate: Predicate, sort: Sort): MutableIterable<T> {
        return repository.findAll(predicate, sort)
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

    fun deleteById(id: ID) {
        return repository.deleteById(id)
    }

    fun deleteAllInBatch() {
        return repository.deleteAllInBatch()
    }

    fun <S : T> saveAndFlush(entity: S): S {
        return repository.saveAndFlush(entity)
    }

    fun flush() {
        return repository.flush()
    }

    fun deleteAll(entities: MutableIterable<T>) {
        return repository.deleteAll(entities)
    }

    fun deleteAll() {
        return repository.deleteAll()
    }

    fun <S : T> saveAll(entities: MutableIterable<S>): MutableList<S> {
        return repository.saveAll(entities)
    }

    fun <S : T> findOne(example: Example<S>): Optional<S> {
        return repository.findOne(example)
    }

    fun findOne(predicate: Predicate): Optional<T> {
        return repository.findOne(predicate)
    }

    fun count(): Long {
        return repository.count()
    }

    fun <S : T> count(example: Example<S>): Long {
        return repository.count(example)
    }

    fun count(predicate: Predicate): Long {
        return repository.count(predicate)
    }

    fun getOne(id: ID): T {
        return repository.getReferenceById(id)
    }

    fun findAllById(ids: MutableIterable<ID>): MutableList<T> {
        return repository.findAllById(ids)
    }

    fun existsById(id: ID): Boolean {
        return repository.existsById(id)
    }

    fun <S : T> exists(example: Example<S>): Boolean {
        return repository.exists(example)
    }

    fun exists(predicate: Predicate): Boolean {
        return repository.exists(predicate)
    }

    fun findById(id: ID): Optional<T> {
        return repository.findById(id)
    }

    fun delete(entity: T) {
        return repository.delete(entity)
    }

}