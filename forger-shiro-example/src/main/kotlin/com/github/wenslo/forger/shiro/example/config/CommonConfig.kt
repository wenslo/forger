package com.github.wenslo.forger.shiro.example.config

import com.github.wenslo.forger.core.util.GsonSingleton
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration

@Configuration
class CommonConfig {
    @Autowired
    fun gson(): Gson {
        return GsonSingleton.getInstance();
    }
    
}