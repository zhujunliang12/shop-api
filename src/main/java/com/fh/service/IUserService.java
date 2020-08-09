package com.fh.service;

import com.fh.model.User;

public interface IUserService {
    User findByUserName(String userName);
}
