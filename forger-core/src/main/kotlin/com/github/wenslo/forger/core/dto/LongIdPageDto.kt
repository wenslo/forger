package com.github.wenslo.forger.core.dto

import java.time.LocalDateTime

abstract class LongIdPageDto(
    var id: Long? = null,
    var createdAt: LocalDateTime? = null,
    var updatedAt: LocalDateTime? = null,
    var createdBy: Long? = null,
    var updatedBy: Long? = null,
)