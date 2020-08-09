package com.fh.member.controller;

import com.fh.annotation.Check;
import com.fh.common.Const;
import com.fh.common.Constant;
import com.fh.common.ServerResponse;
import com.fh.member.biz.IMemberService;
import com.fh.member.po.Member;
import com.fh.utils.KeyUtils;
import com.fh.utils.RedisUtils;
import com.fh.vo.MemberVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("members")
@Api(tags = "会员的接口")
public class MemberController {

    @Resource(name = "memberService")
    private IMemberService memberService;

    /**
     * 增加会员
     *
     * @param member
     * @return
     */
    @PostMapping
    @ApiOperation("增加会员的方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberName", type = "string", value = "会员名", paramType = "query", required = true),
            @ApiImplicitParam(name = "password", type = "string", value = "密码", paramType = "query", required = true),
            @ApiImplicitParam(name = "realName", type = "string", value = "真实姓名", paramType = "query", required = true),
            @ApiImplicitParam(name = "birthday", type = "string", value = "日期", paramType = "query", required = true),
            @ApiImplicitParam(name = "mail", type = "string", value = "邮箱地址", paramType = "query", required = true),
            @ApiImplicitParam(name = "phone", type = "string", value = "手机号", paramType = "query", required = true),
            @ApiImplicitParam(name = "provinceId", type = "long", value = "省id", paramType = "query"),
            @ApiImplicitParam(name = "cityId", type = "long", value = "市id", paramType = "query"),
            @ApiImplicitParam(name = "contryId", type = "long", value = "县id", paramType = "query"),
            @ApiImplicitParam(name = "areaName", type = "string", value = "地区名", paramType = "query")
    })
    public ServerResponse addMember(Member member) {

        return memberService.addMember(member);
    }

    /**
     * 从result 获取用户信息 展示给前台 欢迎xx登录成功
     *
     * @param request
     * @return
     */
    @GetMapping("/findMemberName")
    @Check
    public ServerResponse findMemberName(HttpServletRequest request) {
        MemberVo memberVo = (MemberVo) request.getAttribute(Constant.LOGIN_MERMER_INFO);
        return ServerResponse.success(memberVo);
    }

    /**
     * 退出或注销方法
     *
     * @param request
     * @return
     */
    @GetMapping("/loginOut")
    @Check
    public ServerResponse loginOut(HttpServletRequest request) {
        MemberVo memberVo = (MemberVo) request.getAttribute(Constant.LOGIN_MERMER_INFO);
        Long id = memberVo.getId( );
        String uuid = memberVo.getUuid( );
        RedisUtils.delKey(KeyUtils.memberKey(id, uuid));
        return ServerResponse.success( );
    }

    @GetMapping("/vaildateMemberName")
    public ServerResponse vaildateMemberName(String memberName) {
        return memberService.vaildateMemberName(memberName);
    }

    @GetMapping("/vaildatePhone")
    public ServerResponse vaildatePhone(String phone) {
        return memberService.vaildatePhone(phone);
    }

    @GetMapping("/vaildateMail")
    public ServerResponse vaildateMail(String mail) {
        return memberService.vaildateMail(mail);
    }

    /**
     * token登录方法
     *
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("会员登录方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberName", type = "string", value = "会员名称", required = true, paramType = "query"),
            @ApiImplicitParam(name = "password", type = "string", value = "会员密码", required = true, paramType = "query")
    })
    public ServerResponse login(String memberName, String password) {
        return memberService.login(memberName, password);
    }

    @PostMapping("updateUserPassword")
    @Check
    public ServerResponse updateUserPassword(Member member, String newPassword, String confirmPassword) {
        return memberService.updateUserPassword(member, newPassword, confirmPassword);
    }
}
