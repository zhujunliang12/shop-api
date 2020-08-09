package com.fh.area.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.area.po.Area;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IAreaMapper extends BaseMapper<Area> {


    @Select("select * from mall_area where pid=#{id}")
    List<Area> queryArea(Area area);

    @Select("select * from mall_area where id not in(select id from mall_area where pid=0)")
    List<Area> selectAreaList();
}
