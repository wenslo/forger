package com.github.wenslo.forger.data.jpa.service

import com.github.wenslo.forger.core.condition.DeptIdCondition
import com.github.wenslo.forger.data.jpa.model.DeptIdEntity
import com.github.wenslo.forger.data.jpa.querydsl.DeptIdEntityBasePath
import com.github.wenslo.forger.data.jpa.repository.DeptIdRepository
import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Predicate
import java.lang.reflect.ParameterizedType


interface DeptIdService<T : DeptIdEntity, C : DeptIdCondition> : OrgIdService<T, C> {

    fun findByDeptId(deptId: Long): List<T>?

    fun findByDeptCode(deptCode: String): List<T>?

    fun startWithByDeptCode(deptCode: String): List<T>?
}

abstract class DeptIdServiceImpl<T : DeptIdEntity, C : DeptIdCondition, R : DeptIdRepository<T, Long>> :
    DeptIdService<T, C>,
    OrgIdServiceImpl<T, C, R>() {

    override fun conditionBuild(condition: C): MutableList<Predicate> {
        val builder = super.conditionBuild(condition)
        val pathBase: DeptIdEntityBasePath<T> = getEntityPath()
        val deptId = condition.deptId
        if (deptId != null) {
            builder.add(pathBase.deptId.eq(deptId))
        }
        val deptCode = condition.deptCode
        if (!deptCode.isNullOrBlank()) {
            builder.add(pathBase.deptCode.startsWith(deptCode))
        }
        val deptCodeList = condition.deptCodeList
        if (!deptCodeList.isNullOrEmpty()) {
            val deptOrBuilder = BooleanBuilder()
            deptCodeList.forEach {
                deptOrBuilder.or(pathBase.deptCode.startsWith(it))
            }
            builder.add(deptOrBuilder)
        }
        return builder
    }

    override fun findByDeptId(deptId: Long): List<T>? {
        return repository.findByDeptId(deptId)
    }

    override fun findByDeptCode(deptCode: String): List<T>? {
        return repository.findByDeptCode(deptCode)
    }

    override fun startWithByDeptCode(deptCode: String): List<T>? {
        return repository.startWithByDeptCode(deptCode)
    }

    private fun getEntityPath(): DeptIdEntityBasePath<T> {
        val tClass = getTClass()
        return DeptIdEntityBasePath(
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