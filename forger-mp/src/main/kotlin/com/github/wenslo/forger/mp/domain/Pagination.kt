package com.github.wenslo.forger.mp.domain

import com.baomidou.mybatisplus.core.metadata.OrderItem
import com.google.common.base.CaseFormat

data class Pagination<T>(

    var records: List<T> = emptyList<T>(),

    var total: Long = 0,

    var size: Long = 10,

    var pageing: Boolean = true,

    var fields: List<String> = ArrayList(),

    var query: Map<String, String> = HashMap(),

    var condition: Map<String, String> = HashMap(),

    var custom: Map<String, String> = HashMap(),

    var section: Map<String, String> = HashMap(),
) {
    var current: Long = 1
        get() = if (field <= 0) 1 else field

    var orders: List<OrderItem> = mutableListOf()
        set(value) {
            for (orderItem in value) {
                orderItem.column = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, orderItem.column)
            }
            field = value
        }


}