package com.fh.user.controller;

import com.fh.user.biz.IUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("user")
public class UserContoller {

    @Resource(name = "userService")
    private IUserService userService;


}
