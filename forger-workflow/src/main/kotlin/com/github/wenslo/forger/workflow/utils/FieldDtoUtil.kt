package com.github.wenslo.forger.workflow.utils

import cn.hutool.core.lang.Validator
import cn.hutool.core.util.NumberUtil
import cn.hutool.core.util.StrUtil
import com.github.wenslo.forger.core.exceptions.BusinessException
import com.github.wenslo.forger.core.util.GsonSingleton
import com.github.wenslo.forger.workflow.domain.FieldDto
import com.github.wenslo.forger.workflow.enums.FieldType
import com.github.wenslo.forger.workflow.enums.IsFlag
import java.text.ParseException
import java.text.SimpleDateFormat

/**
 * @author wenhailin
 * @date 2023/1/10 14:16
 */
object FieldDtoUtil {

    fun valid(fields: List<FieldDto>) {
        if (fields.isEmpty()) return

        val collect = fields.associateBy({ it.name }, { it.value })

        for (it in fields) {
            val name = it.name
            if (StrUtil.isBlank(name)) {
                throw BusinessException.PARAM_ERROR
            }
            val value = collect[name]
            if (it.requireFlag == IsFlag.YES) {
                if (value == null) {
                    throw BusinessException.PARAM_ERROR
                }
                if (value is String && StrUtil.isBlank(value)) {
                    throw BusinessException.PARAM_ERROR
                }
            }
            if (value != null && value is String && StrUtil.isNotBlank(value)) {
                val valid = this.valid(it, value)
                if (!valid) {
                    throw BusinessException.PARAM_ERROR
                }
            }
        }
    }

    private fun valid(field: FieldDto, value: String): Boolean {
        val len = field.len
        when (field.type) {
            FieldType.NUMBER -> {
                val isNumber = Validator.isNumber(value)
                if (!isNumber) {
                    return false
                }
                val anInt = NumberUtil.parseInt(value)
                return anInt >= (field.min ?: 0) && anInt <= (field.max ?: 99)
            }

            FieldType.STRING -> {
                return value.length <= (len ?: 0)
            }

            FieldType.ENCRYPT -> {
                return value.length <= (len ?: 0)
            }

            FieldType.DATETIME -> {
                return try {
                    val sdm = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    sdm.parse(value)
                    true
                } catch (e: ParseException) {
                    false
                }
            }

            FieldType.EMAIL -> {
                return Validator.isEmail(value) && value.length <= (len ?: 0)
            }

            FieldType.URL -> {
                return Validator.isUrl(value) && value.length <= (len ?: 0)
            }

            FieldType.IP -> {
                return Validator.isIpv4(value) && value.length <= (len ?: 0)
            }

            FieldType.LINUX_PATH -> {
                return Validator.isMatchRegex("^(/[^/]*)+/?$", value) && value.length <= (len ?: 0)
            }

            else -> {
                return true
            }
        }
    }

    fun <T> convert(fields: List<FieldDto>?, clazz: Class<T>): T? {
        if (fields?.isEmpty() == true) {
            return null
        }
        val map = fields?.associateBy(keySelector = { it.name }, valueTransform = { it.value })
        val gson = GsonSingleton.getInstance()
        return gson.fromJson(gson.toJson(map), clazz)
    }
}