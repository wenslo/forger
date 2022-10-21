package com.github.wenslo.forger.mp.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.github.wenslo.forger.core.condition.LongIdCondition
import com.github.wenslo.forger.core.util.StrCaseUtil
import com.github.wenslo.forger.mp.domain.Pagination
import com.github.wenslo.forger.mp.model.LongIdEntity
import com.github.wenslo.forger.mp.service.LongIdService
import org.springframework.beans.BeanUtils
import java.util.*

open class LongIdServiceImpl<M : BaseMapper<T>, T : LongIdEntity, C : LongIdCondition>
    : LongIdService<T, C>, BaseServiceIntercept<M, T>() {

    protected val idField = "id"
    protected val createdAtField = "created_at"
    protected val updatedAtField = "updated_at"


    override fun add(entity: T): T {
        val beforeResult = saveBefore(entity)
        if (Objects.nonNull(beforeResult)) {
            return beforeResult!!
        }
        super<BaseServiceIntercept>.save(entity)
        val afterResult = saveAfter(entity)
        return if (Objects.nonNull(afterResult)) {
            afterResult!!
        } else entity
    }

    override fun update(entity: T): T {
        val beforeResult = updateBefore(entity)
        if (Objects.nonNull(beforeResult)) {
            return beforeResult!!
        }
        val result: Boolean = super<BaseServiceIntercept>.updateById(entity)
        if (result) {
            val dbRecord = getById(entity.id)
            BeanUtils.copyProperties(dbRecord, entity)
        }
        val afterResult = updateAfter(entity)
        return if (Objects.nonNull(afterResult)) {
            afterResult!!
        } else entity
    }

    override fun remove(entity: T): Map<String, Any>? {
        val beforeResult = removeBefore(entity)
        if (Objects.nonNull(beforeResult)) {
            return beforeResult
        }
        val remove: Boolean = removeById(entity)
        if (remove) {
            val afterResult = removeAfter(entity)
            if (Objects.nonNull(afterResult)) {
                return afterResult
            }
            val result: MutableMap<String, Any> = mutableMapOf()
            result[entity.id.toString()] = true
            return result
        }
        return null
    }

    override fun addBatch(entities: Collection<T>?): List<T>? {
        val beforeResult = addBatchBefore(entities)
        if (beforeResult?.isNotEmpty() == true) {
            return beforeResult
        }
        super<BaseServiceIntercept>.saveBatch(entities)
        val afterResult = addBatchAfter(entities)
        return if (afterResult?.isNotEmpty() == true) {
            afterResult
        } else entities?.toList()
    }

    override fun updateBatch(entities: Collection<T>?): Map<String, Any>? {
        val beforeResult = updateBatchBefore(entities)
        if (beforeResult?.isNotEmpty() == true) {
            return beforeResult
        }
        val result: MutableMap<String, Any> = mutableMapOf()
        for (entity in entities!!) {
            updateById(entity)
            result[entity.id.toString()] = entity
        }
        val afterResult = updateBatchAfter(entities)
        return if (afterResult?.isNotEmpty() == true) {
            afterResult
        } else result
    }

    override fun removeBatch(entities: Collection<T>?): Map<String, Any>? {
        val result: MutableMap<String, Any> = mutableMapOf()
        val beforeResult = removeBatchByGuidBefore(entities)
        if (beforeResult?.isNotEmpty() == true) {
            return beforeResult
        }
        for (entity in entities!!) {
            result[entity.id.toString()] = removeById(entity)
        }
        val afterResult = removeBatchByGuidAfter(entities)
        return if (afterResult?.isNotEmpty() == true) {
            afterResult
        } else result
    }

    override fun detail(entity: T): T {
        val beforeEntity = detailBefore(entity)
        if (Objects.nonNull(beforeEntity)) {
            return beforeEntity!!
        }
        val currentEntity: T = getById(entity.id)
        val afterEntity = detailAfter(currentEntity)
        return if (Objects.nonNull(afterEntity)) {
            afterEntity!!
        } else entity
    }

    override fun queryByPage(pageable: Pagination<T>, condition: C, queryWrapper: QueryWrapper<T>?): Pagination<T>? {
        val page: IPage<T> = Page()
        BeanUtils.copyProperties(pageable, page)
        var wrapper = getQueryWrapper(pageable, condition, queryWrapper)
        val beforeResult = queryByPageBefore(pageable, condition, wrapper)
        if (Objects.nonNull(beforeResult)) {
            return beforeResult
        }
        if (pageable.pageing) {
            super<BaseServiceIntercept>.page(page, wrapper)
            BeanUtils.copyProperties(page, pageable)
        } else {
            val list: List<T> = super<BaseServiceIntercept>.list(wrapper)
            pageable.records = list
            pageable.total = list.size.toLong()
        }
        val afterResult = queryByPageAfter(pageable, condition, wrapper)
        return if (Objects.nonNull(afterResult)) {
            afterResult
        } else pageable
    }

    open fun getQueryWrapper(
        pagination: Pagination<T>,
        condition: C,
        queryWrapper: QueryWrapper<T>?
    ): QueryWrapper<T> {
        var wrapper = QueryWrapper<T>()
        if (Objects.nonNull(queryWrapper)) {
            wrapper = QueryWrapper<T>()
        }
        if (pagination.fields.isNotEmpty()) {
            val toLowerUnderscore = StrCaseUtil.toLowerUnderscore(pagination.fields)
            wrapper.select(*toLowerUnderscore)
        }
        if (pagination.query.isNotEmpty()) {
            for (entry in pagination.query.entries) {
                val key = entry.key
                val value = entry.value
                wrapper.like(StrCaseUtil.toLowerUnderscore(key), value)
            }
        }
        if (pagination.section.isNotEmpty()) {
            for (entry in pagination.section.entries) {
                val key = entry.key
                val value = entry.value
                if (key.startsWith("start_")) {
                    wrapper.ge(StrCaseUtil.toLowerUnderscore(key.replace("start_", "")), value)
                } else if (key.startsWith("end_")) {
                    wrapper.ge(StrCaseUtil.toLowerUnderscore(key.replace("end_", "")), value)
                }
            }
        }
        if (condition.id != null) {
            wrapper.eq(idField, condition.id)
        }
        if (condition.ids?.isNotEmpty() == true) {
            wrapper.`in`(idField, condition.ids)
        }
        if (condition.createdAtStart != null) {
            wrapper.ge(createdAtField, condition.createdAtStart)
        }
        if (condition.createdAtEnd != null) {
            wrapper.le(createdAtField, condition.createdAtEnd)
        }
        if (condition.updatedAtStart != null) {
            wrapper.ge(updatedAtField, condition.updatedAtStart)
        }
        if (condition.updatedAtEnd != null) {
            wrapper.le(updatedAtField, condition.updatedAtEnd)
        }
        return wrapper
    }

    override fun queryByCondition(
        pagination: Pagination<T>,
        condition: C,
        queryWrapper: QueryWrapper<T>?
    ): Pagination<T>? {
        val page: IPage<T> = Page()
        BeanUtils.copyProperties(pagination, page)
        var wrapper = getQueryWrapper(pagination, condition, queryWrapper)
        val beforeResult = queryByConditionBefore(pagination, condition, wrapper)
        if (Objects.nonNull(beforeResult)) {
            return beforeResult
        }
        val list: List<T> = super<BaseServiceIntercept>.list(wrapper)
        pagination.records = list
        pagination.total = list.size.toLong()
        val afterResult = queryByConditionAfter(pagination, condition, wrapper)
        return if (Objects.nonNull(afterResult)) {
            afterResult
        } else pagination
    }

    override fun queryListByCondition(
        pagination: Pagination<T>,
        condition: C,
        queryWrapper: QueryWrapper<T>?
    ): List<T> {
        var wrapper = getQueryWrapper(pagination, condition, queryWrapper)
        val beforeResult = queryListByConditionBefore(pagination, condition, wrapper)
        if (beforeResult?.isNotEmpty() == true) {
            return beforeResult
        }
        val list: List<T> = super<BaseServiceIntercept>.list(wrapper)

        val afterResult = queryListByConditionAfter(pagination, condition, wrapper)
        return if (afterResult?.isNotEmpty() == true) {
            afterResult
        } else list
    }
}