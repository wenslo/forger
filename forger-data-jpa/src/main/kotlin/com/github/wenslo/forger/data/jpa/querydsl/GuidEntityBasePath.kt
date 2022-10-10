package com.github.wenslo.forger.data.jpa.querydsl

import com.querydsl.core.types.dsl.DateTimePath
import com.querydsl.core.types.dsl.EntityPathBase
import com.querydsl.core.types.dsl.PathInits
import java.time.LocalDateTime

class GuidEntityBasePath<T>(
    type: Class<out T>?, variable: String?
) : EntityPathBase<T>(type, variable) {
    companion object {
        private val INITS = PathInits.DIRECT2
    }

    val id = createNumber("id", Long::class.java)
    val guid = createString("guid")
    val createdAt: DateTimePath<LocalDateTime> = createDateTime("createdAt", LocalDateTime::class.java)
    val updatedAt: DateTimePath<LocalDateTime> = createDateTime("updatedAt", LocalDateTime::class.java)
}