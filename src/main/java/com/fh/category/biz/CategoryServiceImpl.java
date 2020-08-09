package com.fh.category.biz;

import com.alibaba.fastjson.JSONObject;
import com.fh.category.mapper.CategoryMapper;
import com.fh.category.po.Category;
import com.fh.utils.KeyUtils;
import com.fh.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("categoryService")
public class CategoryServiceImpl implements ICategoryService {

    @Resource
    private CategoryMapper categoryMapper;


    @Override
    public List<Category> categoryList(Category category) {
        List<Category> categoryList = categoryMapper.selectList(null);
        return categoryList;
    }

    @Override
    public List<Category> queryCategory(List<Category> categoryList, int pid) {
        ArrayList<Category> tempList = new ArrayList<>( );
        for (Category category1 : categoryList) {
            if (category1.getPid( ) == pid) {
                tempList.add(category1);
                List<Category> sonList = queryCategory(categoryList, category1.getId( ));
                if (sonList.size( ) > 0) {
                    category1.setSonList(sonList);
                }
            }
        }
        return tempList;
    }

    /**
     * 分类 进行了缓存
     *
     * @return
     */
    @Override
    public List<Category> queryCategoryList() {
        String categoryList1 = RedisUtils.getKey("categoryList");
        if (StringUtils.isEmpty(categoryList1)) {
            List<Category> categoryList = categoryMapper.selectList(null);
            String category = JSONObject.toJSONString(categoryList);
            RedisUtils.setKey("categoryList", category);
            return categoryList;
        } else {
            List<Category> categoryList = JSONObject.parseArray(categoryList1, Category.class);
            return categoryList;
        }
    }


}
