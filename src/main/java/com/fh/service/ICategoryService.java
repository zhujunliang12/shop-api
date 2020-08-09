package com.fh.service;

import com.fh.common.ServerResponse;
import com.fh.model.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> queryCategory();

    List<Category> treeCategory(List<Category> queryCategoryList, int pid);

    ServerResponse addCategory(Category category);

    ServerResponse deleteCateIds(List<Integer> ids);
}
