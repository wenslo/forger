package com.github.wenslo.forger.core.util

import com.google.gson.*
import org.apache.commons.lang3.StringUtils
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*

object GsonSingleton {
    @Volatile
    private var instance: Gson? = null

    private const val defaultDateTimeFormat = "yyyy-MM-dd HH:mm:ss"

    private fun parseDate(source: String): Date? {
        val simpleDateFormatter = SimpleDateFormat(defaultDateTimeFormat)
        return try {
            simpleDateFormatter.parse(source)
        } catch (e: Exception) {
            null
        }
    }

    fun getInstance(): Gson {
        if (instance == null) {
            synchronized(GsonSingleton::class.java) {
                if (instance == null) {
                    instance = GsonBuilder()
                        .registerTypeAdapter(
                            Date::class.java,
                            JsonDeserializer { json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext? ->
                                val asString = json.asString
                                if (StringUtils.isBlank(asString)) {
                                    return@JsonDeserializer null
                                }
                                parseDate(asString)
                            })
                        .registerTypeAdapter(
                            Int::class.java,
                            JsonDeserializer<Int> { json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext? ->
                                val asString = json.asString
                                if (StringUtils.isBlank(asString)) {
                                    return@JsonDeserializer null
                                }
                                return@JsonDeserializer asString.toInt()
                            })
                        .registerTypeAdapter(
                            Long::class.java,
                            JsonDeserializer<Long> { json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext? ->
                                val asString = json.asString
                                if (StringUtils.isBlank(asString)) {
                                    return@JsonDeserializer null
                                }
                                return@JsonDeserializer asString.toLong()
                            })
                        .setDateFormat(defaultDateTimeFormat)
                        .create()
                }
            }
        }
        return instance!!
    }
}