package com.fh.service.impl;


import com.aliyun.oss.model.OSSObject;
import com.fh.common.Constant;
import com.fh.common.ServerResponse;
import com.fh.mapper.ProductMapper;
import com.fh.model.DataTableResult;
import com.fh.model.Product;
import com.fh.model.SelectProductParam;
import com.fh.service.ProductService;
import com.fh.utils.OssUtil;
import com.fh.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import static java.lang.System.in;

@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductMapper productMapper;

    @Override
    public DataTableResult queryProductList(SelectProductParam selectProductParam) {
        List<Product> queryProductList=productMapper.queryProductList(selectProductParam);
        Long queryCount=productMapper.queryCount(selectProductParam);
        DataTableResult dataTableResult = new DataTableResult(selectProductParam.getDraw(),queryCount,queryCount,queryProductList);
        return dataTableResult;
    }

    @Override
    public ServerResponse changeStatu(Product product) {
        Product pro=productMapper.changeStatu(product);
        if(pro !=null){
            if(pro.getStatus()==0){
                pro.setStatus(1);
            }else{
                pro.setStatus(0);
            }
        }
        RedisUtils.delKey(Constant.REDIS_PRODUCT_KEY);
        productMapper.updateProudct(pro);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse changeHotStatu(Product product) {
        Product pro=productMapper.changeStatu(product);
        if(pro !=null){
            if(pro.getIsHot()==0){
                pro.setIsHot(1);
            }else{
                pro.setIsHot(0);
            }
        }
        RedisUtils.delKey(Constant.REDIS_PRODUCT_KEY);
        productMapper.updateHotStatu(pro);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse addProduct(Product product) {
        product.setCreateTime(new Date());
        RedisUtils.delKey(Constant.REDIS_PRODUCT_KEY);
        productMapper.addProduct(product);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse getProductById(Product product) {
        product=productMapper.getProductById(product);
        return ServerResponse.success(product);
    }

    @Override
    public ServerResponse updateProduct(Product product) {
        product.setUpdateTime(new Date());
       String newImgPath= product.getNewProductImg();
        if(StringUtils.isNotEmpty(newImgPath)){   //说明修改了图片 然后去删除旧图片路径
            if(StringUtils.isNotEmpty(product.getProductImg())){
                OssUtil.deleteFile(product.getProductImg());
                product.setProductImg(product.getNewProductImg());
            }else{
                product.setProductImg(product.getNewProductImg());
            }
        }else{
                product.setProductImg(product.getProductImg());
        }
        RedisUtils.delKey(Constant.REDIS_PRODUCT_KEY);
        productMapper.updateProductData(product);
        return ServerResponse.success();
    }
    //oss下载 有问题 没整出来
    @Override
    public ServerResponse downFiler(Product product, HttpServletResponse response){
        Product productById = productMapper.getProductById(product);
        String productImg = productById.getProductImg( );
        //需要手动设置响应方式
        //告诉浏览器返回的信息是个文件
        response.setContentType("application/x-msdownload");
        //告诉浏览器返回的文件的名称
        response.setHeader("Content-Disposition", "attachment;filename=" + "tttt");
        //通过response把文件信息传给页面
        try {
            Files.copy(Paths.get(productImg), response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ServerResponse.success( );
    }

    @Override
    public List<Product> findProductList(SelectProductParam selectProductParam) {
        List<Product> findProductList=productMapper.findProductList(selectProductParam);
        return findProductList;
    }

}
