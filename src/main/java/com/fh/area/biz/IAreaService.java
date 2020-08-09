package com.fh.area.biz;

import com.fh.area.po.Area;
import com.fh.common.ServerResponse;

public interface IAreaService {
    ServerResponse queryArea(Area area);
}
