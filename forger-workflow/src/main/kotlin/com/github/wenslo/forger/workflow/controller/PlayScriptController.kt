package com.github.wenslo.forger.workflow.controller

import com.github.wenslo.forger.core.inline.getLogger
import com.github.wenslo.forger.workflow.entity.PlayScript
import com.github.wenslo.forger.workflow.service.PlayScriptStage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author wenhailin
 * @date 2022/11/1 21:15
 */
@RestController
@RequestMapping("playScript")
class PlayScriptController {
    val logger = getLogger<PlayScriptController>()

    @Autowired
    lateinit var playScriptStage: PlayScriptStage

    @PostMapping("save")
    fun save(@RequestBody playScript: PlayScript) {
        playScriptStage.paramValid(playScript)
    }

    @PostMapping("saveAndExecute")
    fun saveAndExecute(@RequestBody playScript: PlayScript) {
        playScriptStage.paramValid(playScript)
        playScriptStage.run(playScript)
    }
}