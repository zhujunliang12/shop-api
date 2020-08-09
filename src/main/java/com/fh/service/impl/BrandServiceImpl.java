package com.fh.service.impl;


import com.fh.common.ServerResponse;
import com.fh.mapper.BrandMapper;
import com.fh.model.Brand;
import com.fh.service.IBrandService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;



@Service("brandService")
@Transactional(rollbackFor = Exception.class)
public class BrandServiceImpl implements IBrandService {
    @Resource
    private BrandMapper brandMapper;


    @Override
    public ServerResponse addBrand(Brand brand) {
        brand.setCreateTime(new Date());
        brandMapper.addBrand(brand);
        return ServerResponse.success();

    }

    @Override
    public Brand getBrandById(Integer id) {
      Brand brand=  brandMapper.getBrandById(id);
        return brand;
    }

    @Override
    public ServerResponse updateBrand(Brand brand) {
        brand.setUpdateTime(new Date());
        brandMapper.updateBrand(brand);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse deleteBrand(Integer id) {
        brandMapper.deleteBrand(id);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse deletePath(String ids) {
        if(StringUtils.isNotEmpty(ids)){
             String[] idArr = ids.split(",");
            List<Long> list = null;     //------------------------------------------
            brandMapper.deletePath(list);
        }
        return ServerResponse.success();
    }

    @Override
    @Transactional(readOnly = true)
    public ServerResponse brandList() {
        List<Brand> queryBrandList=brandMapper.brandList();
        return ServerResponse.success(queryBrandList);
    }
}
