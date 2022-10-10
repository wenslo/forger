package com.github.wenslo.forger.data.jpa.service

import com.github.wenslo.forger.core.condition.PageCondition
import com.github.wenslo.forger.core.domain.Pageable
import com.github.wenslo.forger.data.jpa.model.NoArgEntity
import com.github.wenslo.forger.data.jpa.repository.NoArgRepository
import com.google.common.collect.Lists
import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Predicate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import java.io.Serializable
import java.util.function.Consumer

interface NoArgService<T : NoArgEntity, C : PageCondition, ID : Serializable> {

    fun get(id: ID): T?

    fun getAll(): List<T>

    fun save(entity: T): T

    fun save(entities: Iterable<T>): List<T>?

    fun remove(id: ID)

    fun remove(id: List<ID>)

    fun remove(entity: T)

    fun remove(entities: Iterable<T>)

    fun queryByPage(condition: C, pageable: Pageable): Page<T>

    fun queryByCondition(condition: C): List<T>?

    fun findByIdIn(id: List<ID>): List<T>?
}

abstract class NoArgServiceImpl<T : NoArgEntity, C : PageCondition, ID : Serializable> : NoArgService<T, C, ID> {

    @Autowired
    lateinit var repository: NoArgRepository<T, ID>

    override fun get(id: ID): T? {
        return repository.findById(id).orElse(null)
    }

    override fun getAll(): List<T> {
        return repository.findAll()
    }

    override fun save(entity: T): T {
        return repository.save(entity)
    }

    override fun save(entities: Iterable<T>): List<T>? {
        return repository.saveAll(entities)
    }

    override fun remove(id: ID) {
        return repository.deleteById(id)
    }

    override fun remove(id: List<ID>) {
        return repository.deleteByIdIn(id)
    }

    override fun remove(entity: T) {
        return repository.delete(entity)
    }

    override fun remove(entities: Iterable<T>) {
        return repository.deleteAll(entities)
    }

    override fun queryByPage(condition: C, pageable: Pageable): Page<T> {
        return repository.findAll(toPredicate(condition)!!, pageable)
    }

    override fun queryByCondition(condition: C): List<T>? {
        return Lists.newArrayList(repository.findAll(toPredicate(condition)!!))
    }

    override fun findByIdIn(id: List<ID>): List<T>? {
        return repository.findByIdIn(id)
    }


    protected open fun conditionBuild(condition: C): MutableList<Predicate> {
        val conditionBuilder: MutableList<Predicate> = Lists.newArrayList()
        return conditionBuilder
    }

    protected open fun toPredicate(condition: C): Predicate? {
        val predicates: List<Predicate> = conditionBuild(condition)
        val builder = BooleanBuilder()
        predicates.forEach(Consumer { right: Predicate? ->
            builder.and(
                right
            )
        })
        return builder
    }


}