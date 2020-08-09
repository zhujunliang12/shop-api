package com.fh.brand.controller;

import com.fh.brand.biz.IBrandService;
import com.fh.brand.common.ServerResponse;
import com.fh.brand.po.Brand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

@RestController
@RequestMapping("brand")
@Api(tags = "品牌的接口")
public class BrandContoller {

    @Resource(name = "brandService")
    private IBrandService brandService;

    /**
     * 查询品牌数据
     *
     * @return
     */
    @GetMapping
    @ApiOperation("品牌的查询方法")
    public ServerResponse brandList() {
        return brandService.brandList( );
    }

    /**
     * 增加品牌
     *
     * @param brand
     * @return
     */
    @PostMapping
    @ApiOperation("增加品牌的方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", type = "string", required = true, value = "品牌名", paramType = "query"),
            @ApiImplicitParam(name = "isHot", type = "int", value = "热销", paramType = "query"),
            @ApiImplicitParam(name = "logo", type = "string", value = "图片", paramType = "query")
    })
    public ServerResponse addBrand(Brand brand) {

        return brandService.addBrand(brand);
    }

    /**
     * 回显品牌信息
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    @ApiOperation("获取单个品牌信息的方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", type = "int", required = true, paramType = "path")
    })
    public ServerResponse getBrandById(@PathVariable("id") Integer id) {
        Brand brand = brandService.getBrandById(id);
        return ServerResponse.success(brand);
    }


    /**
     * 修改品牌
     *
     * @param brand
     * @return
     */
    @PutMapping
    @ApiOperation("修改品牌的信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", type = "string", required = true, value = "品牌名", paramType = "query"),
            @ApiImplicitParam(name = "isHot", type = "int", value = "热销", paramType = "query"),
            @ApiImplicitParam(name = "logo", type = "string", value = "图片", paramType = "query"),
            @ApiImplicitParam(name = "brandId", type = "int", value = "id号", required = true, paramType = "query")
    })
    public ServerResponse updateBrand(Brand brand) {
        return brandService.updateBrand(brand);
    }

    /**
     * 删除品牌
     *
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    @ApiOperation("删除品牌信息的方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", type = "int", required = true, paramType = "path")
    })
    public ServerResponse deleteBrand(@PathVariable("id") Integer id) {

        return brandService.deleteBrand(id);
    }

    /**
     * 批量删除  ？传参
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public ServerResponse deletePath(String ids) {
        return brandService.deletePath(ids);
    }
}
