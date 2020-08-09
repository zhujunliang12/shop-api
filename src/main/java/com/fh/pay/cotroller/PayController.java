package com.fh.pay.cotroller;

import com.alibaba.fastjson.JSONObject;
import com.fh.annotation.Check;
import com.fh.common.Const;
import com.fh.common.Constant;
import com.fh.common.ServerResponse;
import com.fh.pay.biz.IPayService;
import com.fh.pay.po.PayLog;
import com.fh.utils.KeyUtils;
import com.fh.utils.RedisUtils;
import com.fh.vo.MemberVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

@RestController
@RequestMapping(("pays"))
@Api(tags = "创建微信的接口")
public class PayController {

    @Resource(name = "payService")
    private IPayService payService;

    /**
     * 创建微信
     *
     * @return
     */
    @GetMapping("/createNative")
    @Check
    @ApiOperation("同一下单接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", type = "string", required = true, paramType = "header", value = "头信息")
    })
    public ServerResponse createNative(HttpServletRequest request) {
        MemberVo memberVo = (MemberVo) request.getAttribute(Constant.LOGIN_MERMER_INFO);
        Long memberId = memberVo.getId( );
        String paylogjson = RedisUtils.getKey(KeyUtils.buildPaylogKey(memberId));
        PayLog payLog = JSONObject.parseObject(paylogjson, PayLog.class);
        return payService.createNative(payLog);
    }

    /**
     * 查询订单状态 是已支付 未支付
     *
     * @return
     */
    @GetMapping("/queryPayStatu")
    @Check
    @ApiOperation("查询订单状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", type = "string", required = true, paramType = "header", value = "头信息")
    })
    public ServerResponse queryPayStatu(HttpServletRequest request) {
        MemberVo memberVo = (MemberVo) request.getAttribute(Constant.LOGIN_MERMER_INFO);
        Long memberId = memberVo.getId( );
        String paylogjson = RedisUtils.getKey(KeyUtils.buildPaylogKey(memberId));
        PayLog payLog = JSONObject.parseObject(paylogjson, PayLog.class);
        return payService.queryPayStatu(payLog);
    }


}
