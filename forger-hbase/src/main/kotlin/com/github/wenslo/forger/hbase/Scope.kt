package com.github.wenslo.forger.hbase

/**
 * @author wenhailin
 * @date 2022/11/9 14:27
 */
fun main() {
    var str: String? = "f"
    val caps = str.takeIf { it == "s" }?.uppercase() ?: run {
        "Test"
    }
    println(caps)
}

data class Scope(
    val name: String = "",
    val age: Int = 0
)