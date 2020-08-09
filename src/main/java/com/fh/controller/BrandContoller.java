package com.fh.controller;


import com.fh.common.ServerResponse;
import com.fh.model.Brand;
import com.fh.service.IBrandService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("brand")
public class BrandContoller {

    @Resource(name="brandService")
    private IBrandService brandService;

    /**
     * 查询品牌数据
     * @return
     */
    @GetMapping
    public ServerResponse brandList(){
        return brandService.brandList();
    }

    /**
     * 增加品牌
     * @param brand
     * @return
     */
    @PostMapping
    public ServerResponse addBrand(Brand brand){

        return brandService.addBrand(brand);
    }

    /**
     * 回显品牌信息
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ServerResponse getBrandById(@PathVariable("id")Integer id){
       Brand brand= brandService.getBrandById(id);
        return ServerResponse.success(brand);
    }


    /**
     * 修改品牌
     * @param brand
     * @return
     */
    @PutMapping
    public ServerResponse updateBrand(Brand brand){
        return brandService.updateBrand(brand);
    }
    /**
     * 删除品牌
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public ServerResponse deleteBrand(@PathVariable("id")Integer id){

        return brandService.deleteBrand(id);
    }

    /**
     * 批量删除  ？传参
     * @param ids
     * @return
     */
    @DeleteMapping
    public ServerResponse deletePath(String ids){
       return brandService.deletePath(ids);
    }
}
