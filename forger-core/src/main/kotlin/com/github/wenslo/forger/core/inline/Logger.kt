package com.github.wenslo.forger.core.inline

import org.slf4j.Logger
import org.slf4j.LoggerFactory

inline fun <reified T> getLogger(): Logger {
    return LoggerFactory.getLogger(T::class.java)
}