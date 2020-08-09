package com.fh.receiverInfo.controller;

import com.fh.annotation.Check;
import com.fh.common.Constant;
import com.fh.common.ServerResponse;
import com.fh.receiverInfo.biz.IReceiverService;
import com.fh.receiverInfo.po.ReceiverInfo;
import com.fh.vo.MemberVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("orders")
@Api(tags = "收件人接口")
public class ReceiverContoller {

    @Resource(name = "receiverService")
    private IReceiverService receiverService;

    @GetMapping("/queryOrder")
    @Check
    @ApiOperation("查询收件人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", type = "string", required = true, paramType = "header", value = "头信息")
    })
    public ServerResponse queryOrder(HttpServletRequest request) {
        MemberVo memberVo = (MemberVo) request.getAttribute(Constant.LOGIN_MERMER_INFO);
        Long memberId = memberVo.getId( );
        return receiverService.queryOrder(memberId);
    }

    @PostMapping("/addOrderData")
    @Check
    @ApiOperation("增加收件人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", type = "string", required = true, paramType = "header", value = "头信息"),
            @ApiImplicitParam(name = "receiverName", type = "string", required = true, paramType = "query", value = "收件人名"),
            @ApiImplicitParam(name = "address", type = "string", required = true, paramType = "query", value = "收件人地址"),
            @ApiImplicitParam(name = "receiverPhone", type = "string", required = true, paramType = "query", value = "收件人手机号"),
            @ApiImplicitParam(name = "mail", type = "string", required = true, paramType = "query", value = "收件人邮箱"),
            @ApiImplicitParam(name = "adressName", type = "string", required = true, paramType = "query", value = "收件人现住地址")
    })
    public ServerResponse addOrderData(HttpServletRequest request, ReceiverInfo orderInfo) {
        MemberVo memberVo = (MemberVo) request.getAttribute(Constant.LOGIN_MERMER_INFO);
        Long memberId = memberVo.getId( );
        return receiverService.addOrderData(memberId, orderInfo);
    }

    @PostMapping("/getOrderById")
    @Check
    @ApiOperation("回显收件人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", type = "string", required = true, paramType = "header", value = "头信息")
    })
    public ServerResponse getOrderById(HttpServletRequest request, ReceiverInfo orderInfo) {
        MemberVo memberVo = (MemberVo) request.getAttribute(Constant.LOGIN_MERMER_INFO);
        Long memberId = memberVo.getId( );
        return receiverService.getOrderById(memberId, orderInfo);
    }

    @PostMapping("/deleteOrder")
    @Check
    @ApiOperation("删除收件人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", type = "string", required = true, paramType = "header", value = "头信息")
    })
    public ServerResponse deleteOrder(HttpServletRequest request, ReceiverInfo orderInfo) {
        MemberVo memberVo = (MemberVo) request.getAttribute(Constant.LOGIN_MERMER_INFO);
        Long memberId = memberVo.getId( );
        return receiverService.deleteOrder(memberId, orderInfo);
    }

    @PostMapping("/changeStatu")
    @Check
    @ApiOperation("改变收件人地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", type = "string", required = true, paramType = "header", value = "头信息")
    })
    public ServerResponse changeStatu(HttpServletRequest request, Integer receiverId, Integer statu) {
        MemberVo memberVo = (MemberVo) request.getAttribute(Constant.LOGIN_MERMER_INFO);
        Long memberId = memberVo.getId( );
        return receiverService.changeStatu(memberId, receiverId, statu);
    }

}
