package com.fh.mapper;

import com.fh.model.User;

public interface IUserMapper {

    User findByUserName(String userName);
}
