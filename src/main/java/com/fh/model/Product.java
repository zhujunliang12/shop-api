package com.fh.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Product {

    private Integer productId;
    private String productName;
    private String title;
    private Integer status;
    private Integer isHot;
    private Double price;
    private Integer stock;
    private String productImg;
    private String newProductImg;
    private String remark;
    private String typeNames;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GTM+8")
    private Date createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GTM+8")
    private Date updateTime;

    private Integer brandId;
    private String name;
    private Integer id;
    private Integer gateGoryId1;
    private Integer gateGoryId2;
    private Integer gateGoryId3;


    public String getNewProductImg() {
        return newProductImg;
    }

    public void setNewProductImg(String newProductImg) {
        this.newProductImg = newProductImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGateGoryId1() {
        return gateGoryId1;
    }

    public void setGateGoryId1(Integer gateGoryId1) {
        this.gateGoryId1 = gateGoryId1;
    }

    public Integer getGateGoryId2() {
        return gateGoryId2;
    }

    public void setGateGoryId2(Integer gateGoryId2) {
        this.gateGoryId2 = gateGoryId2;
    }

    public Integer getGateGoryId3() {
        return gateGoryId3;
    }

    public void setGateGoryId3(Integer gateGoryId3) {
        this.gateGoryId3 = gateGoryId3;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsHot() {
        return isHot;
    }

    public void setIsHot(Integer isHot) {
        this.isHot = isHot;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTypeNames() {
        return typeNames;
    }

    public void setTypeNames(String typeNames) {
        this.typeNames = typeNames;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
