package com.github.wenslo.forger.mp.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.github.wenslo.forger.core.condition.LongIdCondition
import com.github.wenslo.forger.core.domain.Pageable
import com.github.wenslo.forger.mp.domain.Pagination
import com.github.wenslo.forger.mp.model.LongIdEntity

open class LongIdIntercept<M : BaseMapper<T>, T : LongIdEntity> : ServiceImpl<M, T>() {
    protected fun saveBefore(entity: T): T? {
        return null
    }

    protected fun saveAfter(entity: T): T? {
        return null
    }

    protected fun removeBefore(entity: T): Map<String, Any>? {
        return null
    }

    protected fun removeAfter(entity: T): Map<String, Any>? {
        return null
    }

    protected fun updateBefore(entity: T): T? {
        return null
    }

    protected fun updateAfter(entity: T): T? {
        return null
    }

    protected fun detailBefore(entity: T): T? {
        return null
    }

    protected fun detailAfter(entity: T): T? {
        return null
    }

    protected fun addBatchBefore(entities: Collection<T>?): List<T>? {
        return null
    }

    protected fun addBatchAfter(entities: Collection<T>?): List<T>? {
        return null
    }

    protected fun updateBatchBefore(entities: Collection<T>?): Map<String, Any>? {
        return null
    }

    protected fun updateBatchAfter(entities: Collection<T>?): Map<String, Any>? {
        return null
    }

    protected fun removeBatchByGuidBefore(entities: Collection<T>?): Map<String, Any>? {
        return null
    }

    protected fun removeBatchByGuidAfter(entities: Collection<T>?): Map<String, Any>? {
        return null
    }

    protected fun <C : LongIdCondition?> queryByPageBefore(
        pageable: Pagination<T>,
        condition: C,
        queryWrapper: QueryWrapper<T>?
    ): Pagination<T>? {
        return null
    }

    protected fun <C : LongIdCondition?> queryByPageAfter(
        pageable: Pagination<T>,
        condition: C,
        queryWrapper: QueryWrapper<T>?
    ): Pagination<T>? {
        return null
    }

    protected fun <C : LongIdCondition?> queryByConditionBefore(
        pageable: Pagination<T>,
        condition: C,
        queryWrapper: QueryWrapper<T>?
    ): Pagination<T>? {
        return null
    }

    protected fun <C : LongIdCondition?> queryByConditionAfter(
        pageable: Pagination<T>,
        condition: C,
        queryWrapper: QueryWrapper<T>?
    ): Pagination<T>? {
        return null
    }

    protected fun <C : LongIdCondition?> queryListByConditionBefore(
        pageable: Pagination<T>,
        condition: C,
        queryWrapper: QueryWrapper<T>?
    ): List<T>? {
        return null
    }

    protected fun <C : LongIdCondition?> queryListByConditionAfter(
        pageable: Pageable,
        condition: C,
        queryWrapper: QueryWrapper<T>?
    ): List<T>? {
        return null
    }
}