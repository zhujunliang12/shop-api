package com.fh.mapper;

import com.fh.common.ServerResponse;
import com.fh.model.Area;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IAreaMapper  {
    @Select("select * from mall_area")
    List<Area> queryAreaList();

    @Insert("insert into mall_area(pid,areaname) values(#{pid},#{areaname})")
    void addArea(Area area);

    void deleteAreaIds(List<Long> listIds);

    @Select("select * from mall_area where pid=#{id}")
    List<Area> queryArea(Area area);
}
