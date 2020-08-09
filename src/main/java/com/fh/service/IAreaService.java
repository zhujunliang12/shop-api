package com.fh.service;

import com.fh.common.ServerResponse;
import com.fh.model.Area;

import java.util.List;

public interface IAreaService {

    List<Area> queryAreaList();

    ServerResponse addArea(Area area);

    ServerResponse deleteAreaIds(List<Long> ids);

    ServerResponse queryArea(Area area);
}
