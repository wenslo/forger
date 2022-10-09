package com.github.wenslo.forger.data.jpa.model

import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(
    AuditingEntityListener::class
)
abstract class GuidEntity(
    @Column(name = "guid")
    var guid: String? = null
) : LongIdEntity()