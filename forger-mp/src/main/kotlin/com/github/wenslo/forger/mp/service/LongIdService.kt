package com.github.wenslo.forger.mp.service

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.service.IService
import com.github.wenslo.forger.core.condition.LongIdCondition
import com.github.wenslo.forger.mp.domain.Pagination
import com.github.wenslo.forger.mp.model.LongIdEntity

interface LongIdService<T : LongIdEntity, C : LongIdCondition> : IService<T> {
    fun add(entity: T): T

    fun update(entity: T): T

    fun remove(entity: T): Map<String, Any>?

    fun addBatch(entities: Collection<T>?): List<T>?

    fun updateBatch(entities: Collection<T>?): Map<String, Any>?

    fun removeBatch(entities: Collection<T>?): Map<String, Any>?

    fun detail(entity: T): T

    fun queryByPage(pageable: Pagination<T>, condition: C, queryWrapper: QueryWrapper<T>?): Pagination<T>?

    fun queryByCondition(pagination: Pagination<T>, condition: C, queryWrapper: QueryWrapper<T>?): Pagination<T>?

    fun queryListByCondition(pagination: Pagination<T>, condition: C, queryWrapper: QueryWrapper<T>?): Pagination<T>?
}