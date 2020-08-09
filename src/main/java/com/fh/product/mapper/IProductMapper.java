package com.fh.product.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.fh.product.po.Product;
import com.fh.vo.ProductVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IProductMapper extends BaseMapper<ProductVo> {

    @Select("select * from mall_product")
    List<Product> productList();

    List<Product> queryProductStock();

    @Select("select * from mall_product where productId=#{goodsId}")
    Product getProductById(Long goodsId);


}
