package com.fh.cart.controller;

import com.fh.annotation.Check;
import com.fh.cart.biz.ICartService;
import com.fh.common.Constant;
import com.fh.common.ServerResponse;
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
@RequestMapping("/cart")
@Api(tags = "购物车操作的接口")

public class CartController {

    @Resource(name = "cartService")
    private ICartService cartService;

    @PostMapping
    @Check
    @ApiOperation("贴加商品到购物车或减少商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", type = "string", required = true, paramType = "header", value = "头信息"),
            @ApiImplicitParam(name = "goodsId", type = "string", required = true, paramType = "query", value = "商品id"),
            @ApiImplicitParam(name = "num", type = "int", required = true, paramType = "query", value = "数量")
    })
    public ServerResponse addCart(HttpServletRequest request, Long goodsId, int num) {
        MemberVo memberVo = (MemberVo) request.getAttribute(Constant.LOGIN_MERMER_INFO);
        Long memberId = memberVo.getId( );
        return cartService.addCart(memberId, goodsId, num);
    }

    /**
     * 查询指定会员的购物车
     *
     * @return
     */
    @GetMapping("queryMemberCartList")
    @Check
    @ApiOperation("获取指定会员的购物车的信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", type = "string", required = true, paramType = "header", value = "头信息")
    })
    public ServerResponse queryMemberCartList(HttpServletRequest request) {
        MemberVo memberVo = (MemberVo) request.getAttribute(Constant.LOGIN_MERMER_INFO);
        Long memberId = memberVo.getId( );
        return cartService.queryMemberCartList(memberId);
    }

    /**
     * 获取被选中商品的信息
     *
     * @return
     */
    @PostMapping("checkProduct")
    @Check
    @ApiOperation("获取被选中商品的信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", type = "string", required = true, paramType = "header", value = "头信息"),
            @ApiImplicitParam(name = "ids", type = "string", required = true, paramType = "query", value = "选中的商品的ids")
    })
    public ServerResponse checkProduct(HttpServletRequest request, @RequestParam("ids") List<Long> ids) {
        MemberVo memberVo = (MemberVo) request.getAttribute(Constant.LOGIN_MERMER_INFO);
        Long memberId = memberVo.getId( );
        return cartService.checkProduct(memberId, ids);
    }

    /**
     * 删除购物车中的商品
     *
     * @return
     */
    @DeleteMapping("/deleteCartProduct")
    @Check
    @ApiOperation("删除购物车中的商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", type = "string", required = true, paramType = "header", value = "头信息"),
            @ApiImplicitParam(name = "productId", type = "long", required = true, paramType = "query", value = "商品的id"),
    })
    public ServerResponse deleteCartProduct(HttpServletRequest request, Long productId) {
        MemberVo memberVo = (MemberVo) request.getAttribute(Constant.LOGIN_MERMER_INFO);
        Long memberId = memberVo.getId( );
        return cartService.deleteCartProduct(memberId, productId);
    }

    /**
     * 删除购物车选中的商品
     *
     * @return
     */
    @DeleteMapping("/deleteCheck")
    @Check
    @ApiOperation("删除购物车选中的商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", type = "string", required = true, paramType = "header", value = "头信息"),
            @ApiImplicitParam(name = "ids", type = "string", required = true, paramType = "query", value = "商品的ids"),
    })
    public ServerResponse deleteCheck(HttpServletRequest request, @RequestParam("ids[]") List<Long> ids) {
        MemberVo memberVo = (MemberVo) request.getAttribute(Constant.LOGIN_MERMER_INFO);
        Long memberId = memberVo.getId( );
        return cartService.deleteCheck(memberId, ids);
    }

    /**
     * 查询关注的商品
     *
     * @param request
     * @return
     */
    @GetMapping("/queryGuanZhuProduct")
    @Check
    @ApiOperation("获取关注的商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", type = "string", required = true, paramType = "header", value = "头信息")
    })
    public ServerResponse queryGuanZhuProduct(HttpServletRequest request) {
        MemberVo memberVo = (MemberVo) request.getAttribute(Constant.LOGIN_MERMER_INFO);
        Long memberId = memberVo.getId( );
        return cartService.queryGuanZhuProduct(memberId);
    }

    /**
     * 查询关注的商品
     *
     * @param request
     * @return
     */
    @PostMapping("/changeAttention")
    @Check
    @ApiOperation("移到我的关注商品中")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", type = "string", required = true, paramType = "header", value = "头信息")
    })
    public ServerResponse changeAttention(HttpServletRequest request, Long goodsId) {
        MemberVo memberVo = (MemberVo) request.getAttribute(Constant.LOGIN_MERMER_INFO);
        Long memberId = memberVo.getId( );
        return cartService.changeAttention(memberId, goodsId);
    }

    /**
     * 查询购物车商品的总数
     *
     * @param request
     * @return
     */
    @GetMapping("/queryCartSumNum")
    @Check
    @ApiOperation("查询购物车商品的总数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", type = "string", required = true, paramType = "header", value = "头信息")
    })
    public ServerResponse queryCartSumNum(HttpServletRequest request) {
        MemberVo memberVo = (MemberVo) request.getAttribute(Constant.LOGIN_MERMER_INFO);
        Long memberId = memberVo.getId( );
        return cartService.queryCartSumNum(memberId);
    }
}
