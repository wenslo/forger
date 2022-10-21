package com.github.wenslo.forger.mp.service

import com.baomidou.mybatisplus.extension.service.IService
import com.github.wenslo.forger.core.condition.GuidCondition
import com.github.wenslo.forger.mp.model.GuidEntity

interface GuidService<T : GuidEntity, C : GuidCondition> : IService<T>, LongIdService<T, C> {

    fun getByGuid(entity: T): T?

    fun updateByGuid(entity: T): Boolean

    fun removeByGuid(entity: T): Boolean

    fun removeBatchByGuid(entities: Collection<T>): Map<String, Any>
}