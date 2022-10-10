package com.github.wenslo.forger.data.jpa.service

import com.github.wenslo.forger.core.condition.OrgIdCondition
import com.github.wenslo.forger.data.jpa.model.OrgIdEntity
import com.github.wenslo.forger.data.jpa.querydsl.OrgIdEntityBasePath
import com.github.wenslo.forger.data.jpa.repository.OrgIdRepository
import com.querydsl.core.types.Predicate
import java.lang.reflect.ParameterizedType

interface OrgIdService<T : OrgIdEntity, C : OrgIdCondition> : GuidService<T, C> {

    fun findByOrgId(orgId: Long): List<T>?

    fun findByOrgIdIn(orgIdList: List<Long>): List<T>?
}

abstract class OrgIdServiceImpl<T : OrgIdEntity, C : OrgIdCondition, R : OrgIdRepository<T, Long>>
    : OrgIdService<T, C>, GuidServiceImpl<T, C, R>() {

    override fun conditionBuild(condition: C): MutableList<Predicate> {
        val conditionBuilder = super.conditionBuild(condition)
        val pathBase: OrgIdEntityBasePath<T> = getEntityPath()
        val orgId = condition.orgId
        if (orgId != null) {
            conditionBuilder.add(pathBase.orgId.eq(orgId))
        }
        val orgIdList = condition.orgIdList
        if (!orgIdList.isNullOrEmpty()) {
            conditionBuilder.add(pathBase.orgId.`in`(orgIdList))
        }
        return conditionBuilder
    }

    override fun findByOrgId(orgId: Long): List<T>? {
        return repository.findByOrgId(orgId)
    }

    override fun findByOrgIdIn(orgIdList: List<Long>): List<T>? {
        return repository.findByOrgIdIn(orgIdList)
    }

    private fun getEntityPath(): OrgIdEntityBasePath<T> {
        val tClass = getTClass()
        return OrgIdEntityBasePath(
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