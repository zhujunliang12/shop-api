package com.fh.token.controller;

import com.fh.annotation.Check;
import com.fh.common.ServerResponse;
import com.fh.token.biz.ITokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/tokens")
@Api(tags = "幂等性生成token的接口")
public class TokenController {

    @Resource(name = "tokenService")
    private ITokenService tokenService;

    @PostMapping("/createToken")
    @Check
    @ApiOperation("创建token用于幂等性的处理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", type = "string", required = true, paramType = "header", value = "头信息")
    })
    public ServerResponse createToken(){
        return tokenService.createToken();
    }


}
