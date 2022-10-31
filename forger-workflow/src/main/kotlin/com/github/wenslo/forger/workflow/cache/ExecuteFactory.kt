package com.github.wenslo.forger.workflow.cache

import com.github.wenslo.forger.workflow.executor.BaseExecutor
import com.github.wenslo.forger.workflow.facade.BaseFacade
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

/**
 * @author wenhailin
 * @date 2022/10/31 21:20
 */
@Component
class ExecuteFactory : InitializingBean, ApplicationContextAware {
    companion object {
        val EXECUTOR_MAP = HashMap<String, BaseExecutor>()
        val FACADE_MAP = HashMap<String, BaseFacade>()
    }

    lateinit var appContext: ApplicationContext


    fun getExecutor(executorId: String) = EXECUTOR_MAP[executorId]

    fun getResourceFacade(executorId: String) = FACADE_MAP[executorId]


    override fun afterPropertiesSet() {
        appContext.getBeansOfType(BaseExecutor::class.java)
            .values
            .forEach {
                EXECUTOR_MAP[it.getResourceInfo().name + it.getResourceInfo().versionNum] = it
            }
        appContext.getBeansOfType(BaseFacade::class.java)
            .values
            .forEach {
                FACADE_MAP[it.getResourceInfo().name + it.getResourceInfo().versionNum] = it
            }
    }

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        appContext = applicationContext
    }


}