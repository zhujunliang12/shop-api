package com.fh.controller;

import com.fh.changLiang.ChangLiang;
import com.fh.common.MD5Util;
import com.fh.common.ResponseEnum;
import com.fh.common.ServerResponse;
import com.fh.model.User;
import com.fh.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("user")
public class UserContoller {

    @Resource(name = "userService")
    private IUserService userService;

    @RequestMapping("login")
    @ResponseBody
    public ServerResponse login(String userName, String password, HttpServletRequest request) {
        User user = userService.findByUserName(userName);
        if (user == null) {
            return ServerResponse.error(ResponseEnum.USER_IS_BULL);
        }
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            return ServerResponse.error(ResponseEnum.USERNAME_IS_NULL);
        }
        if (!password.equals(user.getPassword( ))) {
            return ServerResponse.error(ResponseEnum.USERNAME_IS_NULL);
        }
        request.getSession( ).setAttribute(ChangLiang.CURRENT_USER, user);
        return ServerResponse.success( );
    }


}
