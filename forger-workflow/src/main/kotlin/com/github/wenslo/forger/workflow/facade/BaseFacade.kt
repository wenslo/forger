package com.github.wenslo.forger.workflow.facade

import com.github.wenslo.forger.workflow.domain.ActionDto
import com.github.wenslo.forger.workflow.domain.FieldDto
import com.github.wenslo.forger.workflow.domain.SearchDto

/**
 * @author wenhailin
 * @date 2022/10/26 09:24
 */
interface BaseFacade {
    fun getResourceInfo(): ActionDto

    fun getParameter(): List<FieldDto>

    fun queryRequestFields(): List<FieldDto>

    fun queryResponseFields(): List<FieldDto>

    fun queryThresholdFields(): List<FieldDto>

    fun queryThresholdFieldValues(): List<SearchDto>
}