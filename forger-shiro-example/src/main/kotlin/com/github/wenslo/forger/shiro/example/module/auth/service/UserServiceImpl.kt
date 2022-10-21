package com.github.wenslo.forger.shiro.example.module.auth.service

import com.github.wenslo.forger.mp.service.impl.LongIdServiceImpl
import com.github.wenslo.forger.shiro.core.UserDetails
import com.github.wenslo.forger.shiro.example.module.auth.condition.UserCondition
import com.github.wenslo.forger.shiro.example.module.auth.entity.User
import com.github.wenslo.forger.shiro.example.module.auth.mapper.UserMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl : UserService, LongIdServiceImpl<UserMapper, User, UserCondition>() {
    @Autowired
    lateinit var userMapper: UserMapper

    override fun loadByUsername(username: String): UserDetails? {
        return userMapper.loadByUsername(username)
    }
}