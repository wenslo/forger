package com.github.wenslo.forger.core.util

import com.google.common.base.CaseFormat

class StrCaseUtil {
    companion object {
        @JvmStatic
        fun toLowerUnderscore(s: String?): String {
            return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, s!!)
        }

        fun toLowerUnderscore(array: Collection<String?>): Array<String> {
            val list = ArrayList<String>()
            for (str in array) {
                list.add(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, str!!))
            }
            return list.toTypedArray()
        }

        fun toLowerCamel(s: String?): String {
            return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, s!!)
        }
    }

}