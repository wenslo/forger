package com.github.wenslo.forger.data.jpa.coroutine.service

import com.github.wenslo.forger.core.condition.LongIdCondition
import com.github.wenslo.forger.core.domain.Pageable
import com.github.wenslo.forger.data.jpa.coroutine.repository.LongIdCoroutineRepository
import com.github.wenslo.forger.data.jpa.model.LongIdEntity
import com.github.wenslo.forger.data.jpa.querydsl.LongIdEntityBasePath
import com.github.wenslo.forger.data.jpa.util.BeanUtil
import com.google.common.collect.Lists
import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Predicate
import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import java.lang.reflect.ParameterizedType
import java.util.function.Consumer

interface LongIdCoroutineService<T : LongIdEntity, C : LongIdCondition> {

    suspend fun get(id: Long): T?

    fun getAll(): Flow<T>

    suspend fun save(entity: T): T

    fun save(entities: Iterable<T>): Flow<T>?

    suspend fun remove(id: Long)

    suspend fun remove(id: List<Long>)

    suspend fun remove(entity: T)

    suspend fun remove(entities: Iterable<T>)

    fun queryByPage(condition: C, pageable: Pageable): Page<T>

    fun queryByCondition(condition: C): List<T>?

    suspend fun findByIdIn(id: List<Long>): Flow<T>
}

abstract class LongIdCoroutineServiceImpl<T : LongIdEntity, C : LongIdCondition> : LongIdCoroutineService<T, C> {

    @Autowired
    lateinit var repository: LongIdCoroutineRepository<T, Long>

    override suspend fun get(id: Long): T? {
        return repository.findById(id)
    }

    override fun getAll(): Flow<T> {
        return repository.findAll()
    }

    override suspend fun save(entity: T): T {
        if (entity.id != null) {
            //update
            val one = repository.findById(entity.id!!)
            BeanUtil.copyProperties(one!!, entity)
        }
        return repository.save(entity)
    }

    override fun save(entities: Iterable<T>): Flow<T>? {
        return repository.saveAll(entities)
    }

    override suspend fun remove(id: Long) {
        return repository.deleteById(id)
    }

    override suspend fun remove(id: List<Long>) {
        return repository.deleteByIdIn(id.listIterator())
    }

    override suspend fun remove(entity: T) {
        return repository.delete(entity)
    }

    override suspend fun remove(entities: Iterable<T>) {
        return repository.deleteAll(entities)
    }

    override fun queryByPage(condition: C, pageable: Pageable): Page<T> {
        return repository.findAll(toPredicate(condition)!!, pageable)
    }

    override fun queryByCondition(condition: C): List<T>? {
        return Lists.newArrayList(repository.findAll(toPredicate(condition)!!))
    }

    override suspend fun findByIdIn(id: List<Long>): Flow<T> {
        return repository.findAllById(id)
    }


    protected open fun conditionBuild(condition: C): MutableList<Predicate> {
        val conditionBuilder: MutableList<Predicate> = Lists.newArrayList()
        val pathBase: LongIdEntityBasePath<T> = getEntityPath()!!
        if (condition.id != null) {
            conditionBuilder.add(pathBase.id.eq(condition.id))
        }
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

    private fun getEntityPath(): LongIdEntityBasePath<T>? {
        val tClass = getTClass()
        return LongIdEntityBasePath(
            tClass,
            getName(tClass)
        )
    }

    private fun getName(tClass: Class<T>): String? {
        val nameBuilder = StringBuilder()
        val name = tClass.simpleName
        val letterHead: String = name.substring(0, 1).lowercase()
        nameBuilder.append(letterHead).append(name.substring(1))
        return nameBuilder.toString()
    }

    private fun getTClass(): Class<T> {
        @Suppress("UNCHECKED_CAST")
        return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
    }


}