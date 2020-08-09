package com.fh.service.impl;

import com.fh.mapper.IUserMapper;
import com.fh.model.User;
import com.fh.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userService")
public class UsersServiceImpl implements IUserService {

    @Resource
    private IUserMapper userMapper;

    @Override
    public User findByUserName(String userName) {
       User uuser= userMapper.findByUserName(userName);
        return uuser;
    }
}
