package com.github.wenslo.forger.mp.model

import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import java.time.LocalDateTime

abstract class LongIdEntity(
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null,

    @TableField(value = "created_at", fill = FieldFill.INSERT)
    var createdAt: LocalDateTime? = null,
    
    @TableField(value = "created_at", fill = FieldFill.INSERT_UPDATE)
    var updatedAt: LocalDateTime? = null
)

