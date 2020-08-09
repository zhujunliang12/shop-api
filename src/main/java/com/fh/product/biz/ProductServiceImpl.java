package com.fh.product.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.common.Constant;
import com.fh.common.ServerResponse;
import com.fh.product.mapper.IProductMapper;
import com.fh.product.po.Product;
import com.fh.utils.RedisUtils;
import com.fh.vo.ProductVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("productService")
public class ProductServiceImpl implements IProductService {
    @Resource
    private IProductMapper productMapper;


    @Override
    public ServerResponse queryProductList() {
   /*     QueryWrapper<ProductVo> productQueryWrapper = new QueryWrapper<ProductVo>( );
        productQueryWrapper.eq("1","status");
        productQueryWrapper.eq("1","isHot");
        List<ProductVo> productList = productMapper.selectList(productQueryWrapper);*/
        String productList1 = RedisUtils.getKey(Constant.REDIS_PRODUCT_KEY);
        if (StringUtils.isEmpty(productList1)) {
            List<Product> productList = productMapper.productList( );
            List<ProductVo> productVoList = new ArrayList<>( );
            for (Product product : productList) {
                ProductVo productVo = new ProductVo( );
                productVo.setProductId(product.getProductId( ));
                productVo.setProductName(product.getProductName( ));
                productVo.setStock(product.getStock( ));
                productVo.setProductImg(product.getProductImg( ));
                productVo.setPrice(product.getPrice( ).toString( ));
                productVoList.add(productVo);
            }
            String productVoJson = JSONObject.toJSONString(productVoList);
            RedisUtils.setEx(Constant.REDIS_PRODUCT_KEY, productVoJson, 1 * 60);
            return ServerResponse.success(productList);
        }
        List<ProductVo> productList = JSONObject.parseArray(productList1, ProductVo.class);
        return ServerResponse.success(productList);
    }


}
