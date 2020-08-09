package com.fh.user.biz;

import com.fh.user.mapper.IUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userService")
public class UserServieImpl implements IUserService {
    @Resource
    private IUserMapper userMapper;


}
