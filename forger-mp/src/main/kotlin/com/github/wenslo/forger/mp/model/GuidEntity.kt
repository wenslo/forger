package com.github.wenslo.forger.mp.model

import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.TableField

abstract class GuidEntity(

    @TableField(value = "guid", fill = FieldFill.INSERT)
    var guid: String? = null
) : LongIdEntity()