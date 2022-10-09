package com.github.wenslo.forger.data.jpa.model

import javax.persistence.Column

abstract class DeptIdEntity(
    @Column(name = "dept_id")
    var deptId: Long? = null,
    @Column(name = "dept_code")
    var deptCode: String? = null
) : OrgIdEntity()