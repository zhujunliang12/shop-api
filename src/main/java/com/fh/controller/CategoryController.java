package com.fh.controller;

import com.fh.common.ServerResponse;
import com.fh.model.Category;
import com.fh.service.ICategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("category")
public class CategoryController {

    @Resource(name = "categoryService")
    private ICategoryService categoryService;

    @RequestMapping("initCategory")
    public String initCategory(){
        return "category/initCategory";
    }

     @RequestMapping("queryCategory")
    @ResponseBody
    public ServerResponse queryCategory(){
        List<Category>queryCategoryList=categoryService.queryCategory();
        List<Category>list= categoryService.treeCategory(queryCategoryList,0);
        return ServerResponse.success(queryCategoryList);
    }
   @RequestMapping("addCategory")
    @ResponseBody
    public ServerResponse addCategory(Category category){
        return  categoryService.addCategory(category);
    }
    @RequestMapping("deleteCateIds")
    @ResponseBody
    public ServerResponse deleteCateIds(@RequestParam("ids[]")List<Integer> ids){
        return  categoryService.deleteCateIds(ids);
    }


}
