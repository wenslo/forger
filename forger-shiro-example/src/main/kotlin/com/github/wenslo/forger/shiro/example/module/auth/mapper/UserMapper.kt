package com.github.wenslo.forger.shiro.example.module.auth.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.github.wenslo.forger.shiro.example.module.auth.entity.User
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserMapper : BaseMapper<User> {
    /**
     * load user by username
     */
    fun loadByUsername(username: String): User?
}