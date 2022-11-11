package com.github.wenslo.forger.core.domain

interface BaseEnum {
    fun code(): Int
    fun label(): String
}

data class SimpleEnum(
    val origin: Int,
    val code: Int,
    val label: String
)