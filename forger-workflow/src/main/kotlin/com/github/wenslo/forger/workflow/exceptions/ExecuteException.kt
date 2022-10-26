package com.github.wenslo.forger.workflow.exceptions

/**
 * @author wenhailin
 * @date 2022/10/26 09:07
 */
class ExecuteException(
    override val message: String? = "",
    val e: Exception? = null
) : RuntimeException()