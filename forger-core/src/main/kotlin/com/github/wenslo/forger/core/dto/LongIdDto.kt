package com.github.wenslo.forger.core.dto

import java.time.LocalDateTime

abstract class LongIdDto(
    var id: Long? = null,
    var createdAt: LocalDateTime? = null,
    var updatedAt: LocalDateTime? = null
)