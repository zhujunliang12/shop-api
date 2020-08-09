package com.fh.service.impl;

import com.fh.common.ServerResponse;
import com.fh.mapper.IAreaMapper;
import com.fh.model.Area;
import com.fh.service.IAreaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("areaService")
public class AreaServiceImpl implements IAreaService {
    @Resource
    private IAreaMapper mapper;

    @Override
    public List<Area> queryAreaList() {
        List<Area> queryAreaList=mapper.queryAreaList();
        return queryAreaList;
    }

    @Override
    public ServerResponse addArea(Area area) {
        mapper.addArea(area);
        return  ServerResponse.success();
    }

    @Override
    public ServerResponse deleteAreaIds(List<Long> ids) {
        mapper.deleteAreaIds(ids);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse queryArea(Area area) {
        List<Area>queryArea=mapper.queryArea(area);
        return ServerResponse.success(queryArea);
    }

}
