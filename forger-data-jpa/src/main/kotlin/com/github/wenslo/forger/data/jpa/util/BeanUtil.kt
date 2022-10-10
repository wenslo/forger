package com.github.wenslo.forger.data.jpa.util

import org.springframework.beans.BeanUtils
import org.springframework.beans.BeanWrapper
import org.springframework.beans.BeanWrapperImpl
import java.util.*

class BeanUtil : BeanUtils() {

    companion object {
        fun copyProperties(src: Any, target: Any) {
            BeanUtils.copyProperties(
                src, target, *getNullPropertyNames(
                    src
                )
            )
        }

        private fun getNullPropertyNames(source: Any): Array<String?> {
            val src: BeanWrapper = BeanWrapperImpl(source)
            val pds = src.propertyDescriptors
            val emptyNames: MutableSet<String> = HashSet()
            for (pd in pds) {
                val srcValue = src.getPropertyValue(pd.name)
                if (srcValue == null) {
                    emptyNames.add(pd.name)
                }
            }
            val result = arrayOfNulls<String>(emptyNames.size)
            return emptyNames.toTypedArray()
        }
    }

}