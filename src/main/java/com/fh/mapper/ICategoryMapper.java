package com.fh.mapper;

import com.fh.model.Category;

import java.util.List;

public interface ICategoryMapper {
    List<Category> queryCategory();

    void addCategory(Category category);

    void deleteCateIds(List<Integer> ids);
}
