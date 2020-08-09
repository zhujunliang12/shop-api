package com.fh.mapper;


import com.fh.model.Brand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface BrandMapper {

     @Insert("insert into mall_brand(name,logo,isHot,createTime) values(#{name},#{logo},#{isHot},#{createTime})")
     public void addBrand(Brand brand);

     @Select("select * from mall_brand where brandId=#{id}")
     Brand getBrandById(Integer id);

     @Update("update mall_brand set name=#{name},logo=#{logo},isHot=#{isHot},updateTime=#{updateTime} where brandId=#{brandId}")
     void updateBrand(Brand brand);
     @Delete("delete from mall_brand where brandId=#{id}")
     void deleteBrand(Integer id);

     void deletePath(List<Long> list);

    // 数据库字段 与 实体类名称 不对应 可以使用这个注解
   /* @Results({
            @Result(column = "",property = ""),
            @Result(column = "",property = "")
    })*/
    @Select("select brandId, name,logo,isHot,createTime,updateTime from mall_brand")
     List<Brand> brandList();
}
