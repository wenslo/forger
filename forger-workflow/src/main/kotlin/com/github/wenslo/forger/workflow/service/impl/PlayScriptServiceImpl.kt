package com.github.wenslo.forger.workflow.service.impl

import com.github.wenslo.forger.core.exceptions.BusinessException
import com.github.wenslo.forger.core.inline.getLogger
import com.github.wenslo.forger.data.jpa.service.LongIdServiceImpl
import com.github.wenslo.forger.workflow.cache.ExecuteFactory
import com.github.wenslo.forger.workflow.condition.PlayScriptCondition
import com.github.wenslo.forger.workflow.entity.PlayScript
import com.github.wenslo.forger.workflow.entity.PlayScriptAction
import com.github.wenslo.forger.workflow.entity.PlayScriptParam
import com.github.wenslo.forger.workflow.enums.ActionType
import com.github.wenslo.forger.workflow.repository.*
import com.github.wenslo.forger.workflow.service.PlayScriptService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

/**
 * @author wenhailin
 * @date 2022/11/3 09:26
 */
@Service
@Transactional(readOnly = true)
class PlayScriptServiceImpl : PlayScriptService,
    LongIdServiceImpl<PlayScript, PlayScriptCondition, PlayScriptRepository>() {
    companion object {
        val logger = getLogger<PlayScriptServiceImpl>()
    }

    @Autowired
    lateinit var playScriptRepository: PlayScriptRepository

    @Autowired
    lateinit var playScriptNodeLineRepository: PlayScriptNodeLineRepository

    @Autowired
    lateinit var playScriptNodeRepository: PlayScriptNodeRepository

    @Autowired
    lateinit var playScriptActionRepository: PlayScriptActionRepository

    @Autowired
    lateinit var playScriptActionShuttleRepository: PlayScriptActionShuttleRepository

    @Autowired
    lateinit var playScriptParamRepository: PlayScriptParamRepository

    @Autowired
    lateinit var executeFactory: ExecuteFactory

    @Transactional
    override fun savePlayScript(playScript: PlayScript) {
        val playScriptId = playScript.id
        if (playScriptId == null) {
            playScript.uniqueId = UUID.randomUUID().toString()
            playScript.versionNum += 1
        } else {
            //find max version num
            val optional = playScriptRepository.findById(playScriptId)
            if (!optional.isPresent) {
                throw BusinessException("PlayScript params is error")
            }
            val reference = optional.get()
            val top = playScriptRepository.findTopByUniqueIdOrderByVersionNumDesc(reference.uniqueId)
            val version = (top?.versionNum ?: 0) + 1
            playScript.versionNum = version
            playScript.uniqueId = top?.uniqueId ?: UUID.randomUUID().toString()
            playScript.id = null
        }
        playScriptRepository.save(playScript)
    }

    override fun savePlayScriptNodeLine(playScript: PlayScript) {
        val lines = playScript.lines
        if (lines.isEmpty()) {
            return
        }
        lines.forEach {
            it.playScriptId = playScript.id ?: 0
            it.playScriptUniqueId = playScript.uniqueId
        }
        playScriptNodeLineRepository.saveAll(lines)
    }

    override fun savePlayScriptNode(playScript: PlayScript) {
        val nodes = playScript.nodes
        if (nodes.isEmpty()) {
            return
        }
        nodes.forEach {
            it.playScriptId = playScript.id ?: 0
            it.playScriptUniqueId = playScript.uniqueId
        }
        playScriptNodeRepository.saveAll(nodes)
        this.savePlayScriptAction(playScript)
    }

    override fun savePlayScriptAction(playScript: PlayScript) {
        val nodes = playScript.nodes
        if (nodes.isEmpty()) {
            return
        }
        val fromMap = playScript.lines.groupBy(keySelector = { it.from }, valueTransform = { it.to })
        val toMap = playScript.lines.groupBy(keySelector = { it.to }, valueTransform = { it.from })
        val actions = nodes.map {
            val uniqueId = it.uniqueId
            PlayScriptAction().apply {
                this.playScriptId = it.playScriptId
                this.playScriptUniqueId = it.playScriptUniqueId
                this.uniqueId = uniqueId
                val nextActions = fromMap[uniqueId]
                if (nextActions != null) {
                    this.next = nextActions
                }
                val previousActions = toMap[uniqueId]
                if (previousActions != null) {
                    this.previous = previousActions
                }
                this.executorId = it.executorId
                this.actionType =
                    executeFactory.getExecutor(it.executorId)?.getResourceInfo()?.actionType ?: ActionType.NORMAL
                //asyncFlag cycleFlag cycleCount
            }
        }.toList()
        playScriptActionRepository.saveAll(actions)
    }

    override fun saveParamShuttles(playScript: PlayScript) {
        val shuttles = playScript.shuttles
        if (shuttles.isEmpty()) {
            return
        }
        val list = shuttles.values.flatten().map {
            it.playScriptId = it.playScriptId
            it.playScriptUniqueId = it.playScriptUniqueId
            it
        }.toList()
        playScriptActionShuttleRepository.saveAll(list)
    }

    override fun savePlayScriptParams(playScript: PlayScript) {
        val params = playScript.params
        if (params.isEmpty()) {
            return
        }
        val playScriptId = playScript.id
        val playScriptUniqueId = playScript.uniqueId
        val executorIdMap = playScript.nodes.associateBy({ it.uniqueId }, { it.executorId })
        val list = params.entries.map {
            val actionUniqueId = it.key
            PlayScriptParam().apply {
                this.playScriptId = playScriptId ?: 0
                this.playScriptUniqueId = playScriptUniqueId
                this.actionUniqueId = actionUniqueId
                this.actionExecutorId = executorIdMap[actionUniqueId] ?: ""
                this.params = it.value
            }
        }.toList()
        playScriptParamRepository.saveAll(list)

    }
}