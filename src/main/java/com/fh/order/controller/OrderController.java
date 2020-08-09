package com.fh.order.controller;

import com.fh.annotation.Check;
import com.fh.annotation.IdenToken;
import com.fh.common.Constant;
import com.fh.common.ResponseEnum;
import com.fh.common.ServerResponse;
import com.fh.order.biz.IOrderService;
import com.fh.param.OrderParam;
import com.fh.utils.KeyUtils;
import com.fh.utils.RedisUtils;
import com.fh.vo.MemberVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("order")
@Api(tags = "订单的接口")
public class OrderController {

    @Resource(name = "orderService")
    private IOrderService orderService;

    @PostMapping("/gengerOrder")
    @ApiOperation("生成订单的方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", type = "string", required = true, paramType = "header", value = "头信息"),
            @ApiImplicitParam(name = "ids", type = "string", required = true, paramType = "query", value = "选中的商品")
    })
    @Check
    @IdenToken
    public ServerResponse gengerOrder(HttpServletRequest request, OrderParam orderParam) {
        MemberVo memberVo = (MemberVo) request.getAttribute(Constant.LOGIN_MERMER_INFO);
        Long memberId = memberVo.getId( );
        orderParam.setMemberId(memberId);
        return orderService.gengerOrder(orderParam);
    }

    @GetMapping("/getResult")
    @ApiOperation("获取订单结果")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", type = "string", required = true, paramType = "header", value = "头信息")
    })
    @Check
    public ServerResponse getResult(HttpServletRequest request) {
        MemberVo memberVo = (MemberVo) request.getAttribute(Constant.LOGIN_MERMER_INFO);
        Long memberId = memberVo.getId( );
        if (RedisUtils.exist(KeyUtils.bulidOrderStatuKey(memberId))) {
            RedisUtils.delKey(KeyUtils.bulidOrderStatuKey(memberId));
            return ServerResponse.error(ResponseEnum.ORDER_STOCK_IS_NULL);
        }
        if (RedisUtils.exist(KeyUtils.successOrderKey(memberId))) {
            RedisUtils.delKey(KeyUtils.successOrderKey(memberId));
            return ServerResponse.success( );
        }

        return ServerResponse.error(ResponseEnum.ORDER_IS_QUEUE);
    }
}
