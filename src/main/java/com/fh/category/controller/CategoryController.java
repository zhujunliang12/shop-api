package com.fh.category.controller;

import com.fh.annotation.Check;
import com.fh.category.biz.ICategoryService;
import com.fh.category.po.Category;
import com.fh.common.ServerResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("category")

public class CategoryController {

    @Resource(name = "categoryService")
    private ICategoryService categoryService;

    @GetMapping
    public ServerResponse queryCategory(Category category) {
        List<Category> categoryList = categoryService.categoryList(category);
        List<Category> treeCategory = categoryService.queryCategory(categoryList, 0);
        return ServerResponse.success(treeCategory);
    }

    @RequestMapping(value = "queryCategoryList", method = RequestMethod.GET, produces = "application/json; utf-8")
    public ServerResponse queryCategoryList() {
        List<Category> queryCategoryList = categoryService.queryCategoryList( );
        return ServerResponse.success(queryCategoryList);
    }

}
