package com.github.wenslo.forger.workflow.service.impl

import com.github.wenslo.forger.core.exceptions.BusinessException
import com.github.wenslo.forger.core.inline.getLogger
import com.github.wenslo.forger.data.jpa.service.LongIdServiceImpl
import com.github.wenslo.forger.workflow.cache.ExecuteFactory
import com.github.wenslo.forger.workflow.condition.PlayScriptCondition
import com.github.wenslo.forger.workflow.entity.jpa.PlayScript
import com.github.wenslo.forger.workflow.entity.jpa.PlayScriptAction
import com.github.wenslo.forger.workflow.entity.jpa.PlayScriptActionShuttle
import com.github.wenslo.forger.workflow.entity.jpa.Template
import com.github.wenslo.forger.workflow.entity.mongo.ActionBasicInfo
import com.github.wenslo.forger.workflow.entity.mongo.ActionFields
import com.github.wenslo.forger.workflow.enums.ActionType
import com.github.wenslo.forger.workflow.enums.ExecutorType
import com.github.wenslo.forger.workflow.enums.IsFlag
import com.github.wenslo.forger.workflow.repository.jpa.*
import com.github.wenslo.forger.workflow.repository.mongo.ActionBasicInfoRepository
import com.github.wenslo.forger.workflow.repository.mongo.ActionFieldsRepository
import com.github.wenslo.forger.workflow.service.PlayScriptService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.CollectionUtils
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
    lateinit var actionBasicInfoRepository: ActionBasicInfoRepository

    @Autowired
    lateinit var actionLogBasicInfoRepository: ActionBasicInfoRepository

    @Autowired
    lateinit var actionFieldsRepository: ActionFieldsRepository

    @Autowired
    lateinit var templateActionRepository: TemplateActionRepository

    @Autowired
    lateinit var mongoTemplate: MongoTemplate

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

    @Transactional
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

    @Transactional(readOnly = false)
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
        val fromMap = playScript.lines.groupBy(keySelector = { it.sourceNodeId }, valueTransform = { it.targetNodeId })
        val toMap = playScript.lines.groupBy(keySelector = { it.targetNodeId }, valueTransform = { it.sourceNodeId })
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
                this.executorType = it.executorType
                this.actionType =
                    executeFactory.getExecutor(it.executorType)?.getResourceInfo()?.actionType ?: ActionType.NORMAL
                //asyncFlag cycleFlag cycleCount
            }
        }.toList()
        playScriptActionRepository.saveAll(actions)
    }

    @Transactional(readOnly = false)
    override fun saveParamShuttles(playScript: PlayScript) {
        val shuttles = playScript.shuttles
        if (shuttles.isEmpty()) {
            return
        }
        val list = shuttles.values.flatten().map {
            it.playScriptId = playScript.id ?: 0L
            it.playScriptUniqueId = playScript.uniqueId
            it
        }.toList()
        playScriptActionShuttleRepository.saveAll(list)
    }

    @Transactional(readOnly = false)
    override fun savePlayScriptParams(playScript: PlayScript) {
        val params = playScript.params
        if (params.isEmpty()) {
            return
        }
        val playScriptId = playScript.id
        val playScriptUniqueId = playScript.uniqueId
        val executorTypeMap = playScript.nodes.associateBy({ it.uniqueId }, { it.executorType })
        val list = params.entries.map {
            val actionUniqueId = it.key
            ActionBasicInfo().apply {
                this.playScriptId = playScriptId ?: 0
                this.playScriptUniqueId = playScriptUniqueId
                this.actionUniqueId = actionUniqueId
                this.actionExecutorType = executorTypeMap[actionUniqueId] ?: ExecutorType.NONE
                this.templateFlag = IsFlag.NO
            }
        }.toList()
        actionBasicInfoRepository.saveAll(list)

        //save fields
        val objectIdMap = list.associateBy(keySelector = { it.actionUniqueId }, valueTransform = { it.id })
        val fields = params.entries.map {
            val actionUniqueId = it.key
            it.value.map { field ->
                ActionFields().copyFromFieldDto(field, actionUniqueId, objectIdMap = objectIdMap)
            }.toList()
        }.flatMap { it.toList() }.toList()
        actionFieldsRepository.saveAll(fields)
    }

    override fun findShuttleByPreviousActoin(
        playScriptId: Long,
        actionUniqueId: String
    ): List<PlayScriptActionShuttle> {
        return playScriptActionShuttleRepository.findByPlayScriptIdAndNextActionUniqueId(playScriptId, actionUniqueId)
    }

    @Transactional(readOnly = false)
    override fun saveTemplateParams(playScript: PlayScript) {
        val nodes = playScript.nodes
        val hasWrong = nodes.any {
            (executeFactory.getExecutor(it.executorType)
                ?.getResourceInfo()?.executorType ?: ExecutorType.NONE) == ExecutorType.NONE
        }
        if (hasWrong) {
            throw BusinessException("Executor id is wrong")
        }

        val templateActions = nodes.map {
            executeFactory.getExecutor(it.executorType)
                ?.getResourceInfo()?.executorType ?: ExecutorType.NONE
        }.let {
            templateActionRepository.findByTypeIn(it)
        }
        if (templateActions.isEmpty()) {
            throw BusinessException("Executor type is wrong")
        }

        val templateExecutorMap =
            templateActions.associateBy(keySelector = { it.type }, valueTransform = { it.templateId })

        val templateIdList = templateActions.map { it.templateId }.distinct().toList()
        val query = Query()
        query.addCriteria(Criteria.where("_id").`in`(templateIdList))
        val record = mongoTemplate.find(query, Template::class.java)
        val templateFieldMap = record.associateBy({ it.id }, { it.fields })


        val basics = mutableListOf<ActionBasicInfo>()
        for (node in nodes) {
            val executorType =
                executeFactory.getExecutor(node.executorType)?.getResourceInfo()?.executorType ?: ExecutorType.NONE
            if (executorType == ExecutorType.NONE) {
                throw BusinessException("Illegal templates")
            }
            // find it node belong template's parameter, and saving it
            templateExecutorMap[executorType]?.let {
                val fieldDtoList = templateFieldMap[it]
                if (fieldDtoList?.isEmpty() == true) {
                    throw BusinessException("Template is not installed")
                }
                basics.add(ActionBasicInfo().apply {
                    this.playScriptId = playScript.id ?: 0
                    this.playScriptUniqueId = playScript.uniqueId
                    this.actionUniqueId = node.uniqueId
                    this.actionExecutorType = node.executorType
                    this.templateFlag = IsFlag.YES
                })
            }
        }

        if (basics.isEmpty()) {
            throw BusinessException("Illegal templates")
        }
        actionBasicInfoRepository.saveAll(basics)
        val fields = mutableListOf<ActionFields>()
        //save fields
        val objectIdMap = basics.associateBy(keySelector = { it.actionUniqueId }, valueTransform = { it.id })
        for (node in nodes) {
            val executorType =
                executeFactory.getExecutor(node.executorType)?.getResourceInfo()?.executorType ?: ExecutorType.NONE
            // find it node belong template's parameter, and saving it
            templateExecutorMap[executorType]?.let { it ->
                val fieldDtoList = templateFieldMap[it]
                fieldDtoList?.map { field ->
                    ActionFields().copyFromFieldDto(field, node.uniqueId, objectIdMap = objectIdMap)
                }?.forEach { field -> fields.add(field) }
            }
        }
        actionFieldsRepository.saveAll(fields)
    }

    override fun actionMapByUniqueId(playScriptId: Long): Map<String, PlayScriptAction> {
        //cache it
        val actions = playScriptActionRepository.findByPlayScriptId(playScriptId)
        if (CollectionUtils.isEmpty(actions)) {
            return emptyMap()
        }
        return actions.associateBy { it.uniqueId }
    }

    override fun actionPreviousMap(playScriptId: Long): Map<String, List<String>> {
        //cache it
        val actions = playScriptActionRepository.findByPlayScriptId(playScriptId)
        if (CollectionUtils.isEmpty(actions)) {
            return emptyMap()
        }
        return actions.associateBy(keySelector = { it.uniqueId }, valueTransform = { it.previous })
    }

    override fun actionNextMap(playScriptId: Long): Map<String, List<String>> {
        //cache it
        val actions = playScriptActionRepository.findByPlayScriptId(playScriptId)
        if (CollectionUtils.isEmpty(actions)) {
            return emptyMap()
        }
        return actions.associateBy(keySelector = { it.uniqueId }, valueTransform = { it.next })
    }

    override fun findNextEmpty(playScriptId: Long): List<String> {
        val list =
            playScriptActionRepository.findByNextEmptyAndPlayScriptId(playScriptId)
        if (list.isEmpty()) {
            return emptyList()
        }
        return list.map { it.uniqueId }
    }
}