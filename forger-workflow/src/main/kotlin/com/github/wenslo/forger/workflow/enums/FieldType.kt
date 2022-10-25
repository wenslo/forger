package com.github.wenslo.forger.workflow.enums

import com.github.wenslo.forger.core.domain.BaseEnum

/**
 * @author wenhailin
 * @date 2022/10/25 22:09
 */
enum class FieldType(private val code: String, private val label: String) : BaseEnum {
    NUMBER("1", "NUMBER"),
    STRING("2", "STRING"),
    ENCRYPT("3", "ENCRYPT"),
    DATETIME("4", "DATETIME"),
    EMAIL("5", "EMAIL"),
    URL("6", "URL"),
    IP("7", "IP"),
    LINUX_PATH("8", "LINUX_PATH"),
    THIRD_PARTY("9", "THIRD_PARTY"),
    COMPLEX("10", "COMPLEX");

    override fun code(): String {
        return code
    }

    override fun label(): String {
        return label
    }
}