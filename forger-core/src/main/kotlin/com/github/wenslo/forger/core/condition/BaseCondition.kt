package com.github.wenslo.forger.core.condition

import com.github.wenslo.forger.core.domain.Pageable
import java.time.LocalDateTime


abstract class LongIdCondition(
    var id: Long? = null,
    var ids: List<Long>? = null,
    var createdAtStart: LocalDateTime? = null,
    var createdAtEnd: LocalDateTime? = null,
    var updatedAtStart: LocalDateTime? = null,
    var updatedAtEnd: LocalDateTime? = null
)

abstract class StringIdCondition(
    var id: String? = null
)

abstract class GuidCondition(
    var guid: String? = null,
    var guidList: List<String>? = null
) : LongIdCondition()

abstract class OrgIdCondition(
    var orgId: Long? = null,
    var orgIdList: List<Long>? = null
) : GuidCondition()

abstract class DeptIdCondition(
    var deptId: Long? = null,
    var deptCode: String? = null,
    var deptCodeList: List<String>? = null
) : OrgIdCondition()

//====================================PageCondition down here====================================

abstract class PageCondition : LongIdCondition() {
    var pageable: Pageable? = null
}

abstract class StringPageCondition : StringIdCondition() {
    var pageable: Pageable? = null
}

abstract class GuidPageCondition : GuidCondition() {
    var pageable: Pageable? = null
}

abstract class OrgIdPageCondition : OrgIdCondition() {
    var pageable: Pageable? = null
}

abstract class DeptPageCondition : DeptIdCondition() {
    var pageable: Pageable? = null
}

