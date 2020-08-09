package com.fh.mapper;

import com.fh.model.Product;

import com.fh.model.SelectProductParam;
import org.apache.ibatis.annotations.Param;


import java.util.List;


public interface ProductMapper {
    List<Product> queryProductList(SelectProductParam selectProductParam);
    long queryCount(SelectProductParam selectProductParam);

    Product changeStatu(Product product);

    void updateProudct(Product pro);

    void updateHotStatu(Product pro);

    void addProduct(Product product);

    Product getProductById(Product product);


    void updateProductData(Product product);

    List<Product> findProductList(SelectProductParam selectProductParam);
}
