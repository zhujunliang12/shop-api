package com.fh.product.controller;

import com.fh.annotation.Check;
import com.fh.common.ServerResponse;
import com.fh.product.biz.IProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api(tags = "产品管理相关接口")
@RequestMapping("products")

public class ProductContoller {

    @Resource(name = "productService")
    private IProductService productService;

    /**
     * 查询热销商品接口
     *
     * @return
     */
    @GetMapping
    @ApiOperation("查询热销产品的接口")
    public ServerResponse queryProductList() {
        return productService.queryProductList( );
    }
}
