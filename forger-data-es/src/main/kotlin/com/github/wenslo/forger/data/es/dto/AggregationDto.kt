package com.github.wenslo.forger.data.es.dto

/**
 * 聚合函数封装
 */
data class AggregationDto(
    var key: String? = "",
    var count: Long? = 0
)