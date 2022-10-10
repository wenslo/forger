package com.github.wenslo.forger.data.jpa.service


import com.github.wenslo.forger.core.condition.LongIdCondition
import com.github.wenslo.forger.core.domain.Pageable
import com.github.wenslo.forger.data.jpa.model.LongIdEntity
import com.github.wenslo.forger.data.jpa.querydsl.LongIdEntityBasePath
import com.github.wenslo.forger.data.jpa.repository.LongIdRepository
import com.github.wenslo.forger.data.jpa.util.BeanUtil
import com.google.common.collect.Lists
import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Predicate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import java.lang.reflect.ParameterizedType
import java.time.LocalDateTime
import java.util.*
import java.util.function.Consumer

interface LongIdService<T : LongIdEntity, C : LongIdCondition> {

    fun getBefore(id: Long)

    fun get(id: Long): T?

    fun getAfter(entity: T): T?

    fun getAllBefore()

    fun getAll(): List<T>?

    fun getAllAfter(list: List<T>): List<T>?

    fun saveBefore(entity: T)

    fun save(entity: T): T

    fun saveAfter(entity: T): T

    fun saveBefore(entities: Iterable<T>)

    fun save(entities: Iterable<T>): List<T>?

    fun saveAfter(entities: Iterable<T>): List<T>?

    fun removeBefore(id: Long)

    fun remove(id: Long)

    fun removeAfter(id: Long)

    fun removeBefore(idList: List<Long>)

    fun remove(idList: List<Long>)

    fun removeAfter(idList: List<Long>)

    fun removeBefore(entity: T)

    fun remove(entity: T)

    fun removeAfter(entity: T)

    fun removeBefore(entities: Iterable<T>)

    fun remove(entities: Iterable<T>)

    fun removeAfter(entities: Iterable<T>)

    fun queryByPageBefore(condition: C, pageable: Pageable)

    fun queryByPage(condition: C, pageable: Pageable): Page<T>

    fun queryByPageAfter(condition: C, pageable: Pageable, page: Page<T>): Page<T>

    fun queryByConditionBefore(condition: C)

    fun queryByCondition(condition: C): List<T>?

    fun queryByConditionAfter(condition: C, list: ArrayList<T>): List<T>?

    fun findByIdInBefore(idList: List<Long>)

    fun findByIdIn(idList: List<Long>): List<T>?

    fun findByIdInAfter(idList: List<Long>, list: List<T>?): List<T>?
}

abstract class LongIdServiceImpl<T : LongIdEntity, C : LongIdCondition, R : LongIdRepository<T, Long>> :
    LongIdService<T, C> {

    @Autowired
    open lateinit var repository: R

    override fun getBefore(id: Long) {}

    override fun get(id: Long): T? {
        getBefore(id)
        val entity = repository.findById(id).orElse(null)
        return getAfter(entity)
    }

    override fun getAfter(entity: T): T? {
        return entity
    }


    override fun getAllBefore() {}

    override fun getAll(): List<T>? {
        getAllBefore()
        val list = repository.findAll()
        return getAllAfter(list)
    }

    override fun getAllAfter(list: List<T>): List<T>? {
        return list
    }


    override fun saveBefore(entity: T) {}

    override fun save(entity: T): T {
        saveBefore(entity)
        if (entity.id != null) {
            //update
            val one = repository.getReferenceById(entity.id!!)
            BeanUtil.copyProperties(one, entity)
        }
        val entity = repository.save(entity)
        return saveAfter(entity)
    }

    override fun saveAfter(entity: T): T {
        return entity;
    }


    override fun saveBefore(entities: Iterable<T>) {}

    override fun save(entities: Iterable<T>): List<T>? {
        saveBefore(entities)
        val entities = repository.saveAll(entities)
        return saveAfter(entities)
    }

    override fun saveAfter(entities: Iterable<T>): List<T>? {
        return entities.toList()
    }


    override fun removeBefore(id: Long) {}

    override fun remove(id: Long) {
        removeBefore(id)
        repository.deleteById(id)
        removeAfter(id)
    }

    override fun removeAfter(id: Long) {}


    override fun removeBefore(idList: List<Long>) {}

    override fun remove(idList: List<Long>) {
        removeBefore(idList)
        repository.deleteByIdIn(idList)
        removeAfter(idList)
    }

    override fun removeAfter(idList: List<Long>) {}


    override fun removeBefore(entity: T) {}

    override fun remove(entity: T) {
        removeBefore(entity)
        repository.delete(entity)
        removeAfter(entity)
    }

    override fun removeAfter(entity: T) {}


    override fun removeBefore(entities: Iterable<T>) {}

    override fun remove(entities: Iterable<T>) {
        removeBefore(entities)
        repository.deleteAll(entities)
        removeAfter(entities)
    }

    override fun removeAfter(entities: Iterable<T>) {}

    override fun queryByPageBefore(condition: C, pageable: Pageable) {}

    override fun queryByPage(condition: C, pageable: Pageable): Page<T> {
        queryByPageBefore(condition, pageable)
        val page = repository.findAll(toPredicate(condition)!!, pageable)
        return queryByPageAfter(condition, pageable, page)
    }

    override fun queryByPageAfter(condition: C, pageable: Pageable, page: Page<T>): Page<T> {
        return page;
    }

    override fun queryByConditionBefore(condition: C) {}

    override fun queryByCondition(condition: C): List<T>? {
        this.queryByConditionBefore(condition)
        val list = Lists.newArrayList(repository.findAll(toPredicate(condition)!!))
        return queryByConditionAfter(condition, list)
    }

    override fun queryByConditionAfter(condition: C, list: ArrayList<T>): List<T>? {
        return list
    }


    override fun findByIdInBefore(idList: List<Long>) {

    }

    override fun findByIdIn(idList: List<Long>): List<T>? {
        this.findByIdInBefore(idList)
        val list = repository.findByIdIn(idList)
        return findByIdInAfter(idList, list)
    }

    override fun findByIdInAfter(idList: List<Long>, list: List<T>?): List<T>? {
        return list
    }


    protected open fun conditionBuild(condition: C): MutableList<Predicate> {
        val conditionBuilder: MutableList<Predicate> = Lists.newArrayList()
        val pathBase: LongIdEntityBasePath<T> = getEntityPath()!!
        if (condition.id != null) {
            conditionBuilder.add(pathBase.id.eq(condition.id))
        }
        val createdAtStart: LocalDateTime? = condition.createdAtStart
        val createdAtEnd: LocalDateTime? = condition.createdAtEnd
        if (Objects.nonNull(createdAtStart) && Objects.nonNull(createdAtEnd)) {
            conditionBuilder.add(pathBase.createdAt.between(createdAtStart, createdAtEnd))
        } else {
            if (Objects.nonNull(createdAtStart)) {
                conditionBuilder.add(pathBase.createdAt.goe(createdAtStart))
            }
            if (Objects.nonNull(createdAtEnd)) {
                conditionBuilder.add(pathBase.updatedAt.loe(createdAtEnd))
            }
        }

        val updatedAtStart: LocalDateTime? = condition.updatedAtStart
        val updatedAtEnd: LocalDateTime? = condition.updatedAtEnd
        if (Objects.nonNull(updatedAtStart) && Objects.nonNull(updatedAtEnd)) {
            conditionBuilder.add(pathBase.updatedAt.between(updatedAtStart, updatedAtEnd))
        } else {
            if (Objects.nonNull(updatedAtStart)) {
                conditionBuilder.add(pathBase.updatedAt.goe(updatedAtStart))
            }
            if (Objects.nonNull(updatedAtEnd)) {
                conditionBuilder.add(pathBase.updatedAt.loe(updatedAtEnd))
            }
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
        val letterHead: String = name.substring(0, 1).toLowerCase()
        nameBuilder.append(letterHead).append(name.substring(1))
        return nameBuilder.toString()
    }

    private fun getTClass(): Class<T> {
        @Suppress("UNCHECKED_CAST")
        return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
    }

}