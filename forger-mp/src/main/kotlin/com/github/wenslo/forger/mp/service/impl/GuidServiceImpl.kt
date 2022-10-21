package com.github.wenslo.forger.mp.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.github.wenslo.forger.core.condition.GuidCondition
import com.github.wenslo.forger.mp.model.GuidEntity
import com.github.wenslo.forger.mp.service.GuidService
import com.google.common.collect.Maps

open class GuidServiceImpl<M : BaseMapper<T>, T : GuidEntity, C : GuidCondition>
    : GuidService<T, C>, LongIdServiceImpl<M, T, C>() {

    protected val guidField = "guid"

    override fun getByGuid(entity: T): T? {
        if (entity.guid.isNullOrBlank()) {
            return null
        }
        val queryWrapper = QueryWrapper<T>()
        queryWrapper.eq(guidField, entity.guid)
        return super<LongIdServiceImpl>.getOne(queryWrapper)
    }

    override fun updateByGuid(entity: T): Boolean {
        if (entity.guid.isNullOrBlank()) {
            return false
        }
        val queryWrapper = QueryWrapper<T>()
        queryWrapper.eq(guidField, entity.guid)
        return super<LongIdServiceImpl>.update(entity, queryWrapper)
    }

    override fun removeByGuid(entity: T): Boolean {
        if (entity.guid.isNullOrBlank()) {
            return false
        }
        val queryWrapper = QueryWrapper<T>()
        queryWrapper.eq(guidField, entity.guid)
        return super<LongIdServiceImpl>.remove(queryWrapper)
    }

    override fun removeBatchByGuid(entities: Collection<T>): Map<String, Any> {
        val result: MutableMap<String, Any> = Maps.newHashMap()
        for (entity in entities) {
            result[entity.guid ?: ""] = removeByGuid(entity)
        }
        return result
    }


}