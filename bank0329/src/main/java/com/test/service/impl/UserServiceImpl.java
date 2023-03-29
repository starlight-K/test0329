package com.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.entity.User;
import com.test.mapper.UserMapper;
import com.test.service.UserService;

public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
