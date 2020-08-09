package com.fh.area.biz;

import com.alibaba.fastjson.JSONObject;
import com.fh.area.mapper.IAreaMapper;
import com.fh.area.po.Area;
import com.fh.common.ServerResponse;
import com.fh.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("areaService")
public class AreaServiceImpl implements IAreaService {
    @Resource
    private IAreaMapper areaMapper;

    @Override
    public ServerResponse queryArea(Area area) {
        String areaStr = RedisUtils.getKey("area");
        if (StringUtils.isNotEmpty(areaStr)) {
            List<Area> areaList = areaMapper.selectAreaList( );
            ArrayList<Area> tempList = new ArrayList<>( );
            for (int i = 0; i < areaList.size( ); i++) {
                if (areaList.get(i).getPid( ) == area.getId( )) {
                    tempList.add(areaList.get(i));
                }
            }
            return ServerResponse.success(tempList);
        }
        List<Area> areaList = areaMapper.selectAreaList( );
        String s = JSONObject.toJSONString(areaList);
        RedisUtils.setKey("area", s);
        ArrayList<Area> tempList = new ArrayList<>( );
        for (int i = 0; i < areaList.size( ); i++) {
            if (areaList.get(i).getPid( ) == area.getId( )) {
                tempList.add(areaList.get(i));
            }
        }
        return ServerResponse.success(tempList);
    }
}
