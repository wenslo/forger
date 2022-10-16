package com.github.wenslo.forger.mp.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.github.wenslo.forger.core.condition.LongIdCondition
import com.github.wenslo.forger.core.domain.Pageable
import com.github.wenslo.forger.mp.model.LongIdEntity
import com.github.wenslo.forger.mp.service.LongIdService
import org.springframework.beans.BeanUtils
import java.util.*

open class LongIdServiceImpl<M : BaseMapper<T>, T : LongIdEntity, C : LongIdCondition>
    : LongIdService<T, C>, LongIdIntercept<M, T>() {
    override fun add(entity: T): T {
        val beforeResult = saveBefore(entity)
        if (Objects.nonNull(beforeResult)) {
            return beforeResult!!
        }
        super<LongIdIntercept>.save(entity)
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
        val result: Boolean = super<LongIdIntercept>.updateById(entity)
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
        super<LongIdIntercept>.saveBatch(entities)
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

    override fun queryByPage(pageable: Pageable, condition: C, queryWrapper: QueryWrapper<T>?): Page<T>? {
        TODO("Not yet implemented")
    }

    override fun queryByCondition(pagination: Pageable, condition: C, queryWrapper: QueryWrapper<T>?): Page<T>? {
        TODO("Not yet implemented")
    }

    override fun queryListByCondition(pagination: Pageable, condition: C, queryWrapper: QueryWrapper<T>?): List<T>? {
        TODO("Not yet implemented")
    }
}