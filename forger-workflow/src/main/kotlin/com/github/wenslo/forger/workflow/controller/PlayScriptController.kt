package com.github.wenslo.forger.workflow.controller

import com.github.wenslo.forger.workflow.service.PlayScriptStage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author wenhailin
 * @date 2022/11/1 21:15
 */
@RestController
@RequestMapping("playScript")
class PlayScriptController {
    @Autowired
    lateinit var playScriptStage: PlayScriptStage

    @PostMapping("save")
    fun save() {
        playScriptStage.paramValid()
    }

    @PostMapping("saveAndExecute")
    fun saveAndExecute() {
        playScriptStage.paramValid()
    }
}