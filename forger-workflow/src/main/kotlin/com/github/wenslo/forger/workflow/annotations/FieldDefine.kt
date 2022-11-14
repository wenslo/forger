package com.github.wenslo.forger.workflow.annotations

import com.github.wenslo.forger.workflow.enums.FieldType
import com.github.wenslo.forger.workflow.enums.IsFlag

/**
 * @author wenhailin
 * @date 2022/11/14 10:04
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
annotation class FieldDefine(
    val describe: String,
    val type: FieldType = FieldType.STRING,
    val requireFlag: IsFlag = IsFlag.YES,
    val min: Int = 0,
    val max: Int = 9999,
    val len: Int = 200,
    val sortNum: Int = 99,
    val thresholdUsed: IsFlag = IsFlag.NO
)