package com.fh.service;


import com.fh.common.ServerResponse;
import com.fh.model.Brand;

public interface IBrandService {
    ServerResponse addBrand(Brand brand);

    Brand getBrandById(Integer id);

    ServerResponse updateBrand(Brand brand);

    ServerResponse deleteBrand(Integer id);

    ServerResponse deletePath(String ids);

    ServerResponse brandList();
}
