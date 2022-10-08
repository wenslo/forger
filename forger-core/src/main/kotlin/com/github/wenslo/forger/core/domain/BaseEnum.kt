package com.github.wenslo.forger.core.domain

interface BaseEnum {
    fun code(): String
    fun label(): String
}

data class SimpleEnum(
    val origin: Int,
    val code: String,
    val label: String
)