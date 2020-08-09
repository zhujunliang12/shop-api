package com.fh.controller;

import com.fh.common.ServerResponse;
import com.fh.model.Area;
import com.fh.service.IAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("area")
public class AreaController {

    @Resource(name = "areaService")
    private IAreaService areaService;

    @RequestMapping("initArea")
    public String initArea(){

        return "area/initArea";
    }

    @GetMapping
    @ResponseBody
    public ServerResponse queryArea( Area area)
    {
        return areaService.queryArea(area);
    }

    @RequestMapping("queryArea")
    @ResponseBody
    public List<Area> queryAreaList(){
        List<Area> queryAreaList=areaService.queryAreaList();
        return queryAreaList;
    }

    @RequestMapping("addArea")
    @ResponseBody
    public ServerResponse addArea(Area area){

        return areaService.addArea(area);
    }

    @RequestMapping("deleteAreaIds")
    @ResponseBody
    public ServerResponse deleteAreaIds(@RequestParam("ids[]")List<Long> ids){
        return areaService.deleteAreaIds(ids);
    }


}
