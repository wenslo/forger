package com.github.wenslo.forger.shiro.example.module.auth.service

import com.github.wenslo.forger.mp.service.LongIdService
import com.github.wenslo.forger.shiro.example.module.auth.condition.UserCondition
import com.github.wenslo.forger.shiro.example.module.auth.entity.User
import com.github.wenslo.forger.shiro.service.UserDetailsService

interface UserService : LongIdService<User, UserCondition>, UserDetailsService {

}