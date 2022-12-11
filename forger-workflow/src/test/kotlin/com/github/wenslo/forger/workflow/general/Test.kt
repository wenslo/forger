package com.github.wenslo.forger.workflow.general

/**
 * @author wenhailin
 * @date 2022/12/8 12:52
 */
fun main() {
    val list = listOf(1, 2, 3, 4, 5, 6)
    val joinToString = list.map { it.toString() }.joinToString("|")
    println(joinToString)
}