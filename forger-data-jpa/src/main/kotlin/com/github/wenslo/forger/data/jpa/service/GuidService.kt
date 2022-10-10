package com.github.wenslo.forger.data.jpa.service

import com.github.wenslo.forger.core.condition.GuidCondition
import com.github.wenslo.forger.data.jpa.model.GuidEntity
import com.github.wenslo.forger.data.jpa.querydsl.GuidEntityBasePath
import com.github.wenslo.forger.data.jpa.repository.GuidRepository
import com.querydsl.core.types.Predicate
import org.springframework.beans.factory.annotation.Autowired
import java.lang.reflect.ParameterizedType

interface GuidService<T : GuidEntity, C : GuidCondition> : LongIdService<T, C> {
    fun getByGuid(guid: String): T?

    fun getByGuidIn(list: List<String>): List<T>?
}

abstract class GuidServiceImpl<T : GuidEntity, C : GuidCondition, R : GuidRepository<T, Long>>
    : GuidService<T, C>, LongIdServiceImpl<T, C, R>() {
    @Autowired
    override lateinit var repository: R

    override fun conditionBuild(condition: C): MutableList<Predicate> {
        val conditionBuilder = super.conditionBuild(condition)
        val pathBase: GuidEntityBasePath<T> = getEntityPath()
        val guid: String? = condition.guid
        if (!guid.isNullOrEmpty()) {
            conditionBuilder.add(pathBase.guid.eq(guid))
        }
        val guidList: List<String>? = condition.guidList
        if (!guidList.isNullOrEmpty()) {
            conditionBuilder.add(pathBase.guid.`in`(guidList))
        }
        return conditionBuilder
    }

    override fun getByGuid(guid: String): T? {
        return repository.findOneByGuid(guid)
    }

    override fun getByGuidIn(list: List<String>): List<T>? {
        return repository.findByGuidIn(list)
    }

    private fun getEntityPath(): GuidEntityBasePath<T> {
        val tClass = getTClass()
        return GuidEntityBasePath(
            tClass,
            getName(tClass)
        )
    }

    private fun getName(tClass: Class<T>): String {
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