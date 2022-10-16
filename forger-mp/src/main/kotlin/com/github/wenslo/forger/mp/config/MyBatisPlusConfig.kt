package com.github.wenslo.forger.mp.config

import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MyBatisPlusConfig {
    @Bean
    fun paginationInnerInterceptor(): PaginationInnerInterceptor? {
        val paginationInterceptor = PaginationInnerInterceptor()
        // if request page num is greater then max page num, set ture jump over to first page
        paginationInterceptor.isOverflow = true
        // max limit num per page
        paginationInterceptor.maxLimit = 1000L
        return paginationInterceptor
    }
}