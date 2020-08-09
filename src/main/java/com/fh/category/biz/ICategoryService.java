package com.fh.category.biz;

import com.fh.category.po.Category;
import com.fh.common.ServerResponse;

import java.util.List;

public interface ICategoryService {

    List<Category> categoryList(Category category);


    List<Category> queryCategory(List<Category> categoryList, int pid);

    List<Category> queryCategoryList();
}
