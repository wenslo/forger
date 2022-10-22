package com.github.wenslo.forger.core.exceptions

data class BusinessException(
    override val message: String? = "",
    val e: Exception? = null
) : RuntimeException()