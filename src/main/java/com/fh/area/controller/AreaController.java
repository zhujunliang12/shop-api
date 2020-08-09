package com.fh.area.controller;

import com.fh.area.biz.IAreaService;
import com.fh.area.po.Area;
import com.fh.common.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("areas")

public class AreaController {

    @Autowired
    private IAreaService areaService;


    @GetMapping
    public ServerResponse queryArea(Area area) {
        return areaService.queryArea(area);
    }


}
