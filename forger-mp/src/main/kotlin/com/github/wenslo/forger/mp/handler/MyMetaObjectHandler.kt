package com.github.wenslo.forger.mp.handler

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler
import org.apache.ibatis.reflection.MetaObject
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*


@Component
class MyMetaObjectHandler : MetaObjectHandler {
    override fun insertFill(metaObject: MetaObject) {
        this.strictInsertFill(metaObject, "createdAt", LocalDateTime::class.java, LocalDateTime.now())
        this.strictInsertFill(metaObject, "guid", String::class.java, UUID.randomUUID().toString())
    }

    override fun updateFill(metaObject: MetaObject) {
        this.strictUpdateFill(metaObject, "updatedAt", LocalDateTime::class.java, LocalDateTime.now())
    }
}