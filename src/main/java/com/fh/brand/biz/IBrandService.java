package com.fh.brand.biz;

import com.fh.brand.common.ServerResponse;
import com.fh.brand.po.Brand;

public interface IBrandService {
    ServerResponse addBrand(Brand brand);

    Brand getBrandById(Integer id);

    ServerResponse updateBrand(Brand brand);

    ServerResponse deleteBrand(Integer id);

    ServerResponse deletePath(String ids);

    ServerResponse brandList();
}
