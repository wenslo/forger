package com.github.wenslo.forger.data.jpa.convert

import javax.persistence.AttributeConverter
import javax.persistence.Converter


@Converter
class StringListConverter : AttributeConverter<List<String>, String> {
    override fun convertToDatabaseColumn(attribute: List<String>?): String {
        if (attribute?.isNotEmpty() == true) {
            return attribute.joinToString(separator = ",")
        }
        return ""
    }

    override fun convertToEntityAttribute(dbData: String?): List<String> {
        if (dbData.isNullOrBlank()) {
            return ArrayList()
        }
        return dbData.split(",").toList()
    }
}