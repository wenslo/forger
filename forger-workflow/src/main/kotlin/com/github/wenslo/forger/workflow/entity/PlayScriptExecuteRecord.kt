package com.github.wenslo.forger.workflow.entity

import com.github.wenslo.forger.data.jpa.convert.StringListConverter
import com.github.wenslo.forger.data.jpa.model.LongIdEntity
import com.github.wenslo.forger.workflow.enums.PlayScriptProcessStatus
import javax.persistence.Convert
import javax.persistence.Entity

/**
 * @author wenhailin
 * @date 2022/10/27 10:44
 */
@Entity
data class PlayScriptExecuteRecord(
    var playScriptId: Long = 0,
    var playScriptUniqueId: String = "",
    var processStatus: PlayScriptProcessStatus = PlayScriptProcessStatus.NONE,
    @Convert(converter = StringListConverter::class)
    var current: List<String> = emptyList(),
    var statusCode: Int = 0
) : LongIdEntity()