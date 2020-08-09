package com.fh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fh.common.Constant;
import com.fh.common.ServerResponse;
import com.fh.mapper.ICategoryMapper;
import com.fh.service.JedisUtil;
import com.fh.model.Category;
import com.fh.service.ICategoryService;
import com.fh.utils.RedisUtils;
import com.fh.utils.SerializeUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.tools.ant.taskdefs.Concat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("categoryService")
public class CatetoryServiceImpl implements ICategoryService {

    @Resource
    private ICategoryMapper categoryMapper;


    @Override
    public List<Category> queryCategory() {
        String categoryDB = RedisUtils.getKey(Constant.REDIS_CATETORY_KEY);
        if(StringUtils.isEmpty(categoryDB)){
            List<Category> categoryList = categoryMapper.queryCategory();
            RedisUtils.setKey(Constant.REDIS_CATETORY_KEY, JSONObject.toJSONString(categoryList));
            return categoryList;
        }
        List<Category> list = JSONObject.parseArray(categoryDB, Category.class);
        return  list;
    }

    @Override
    public List<Category> treeCategory(List<Category> queryCategoryList, int pid) {
        ArrayList<Category> tempList = new ArrayList<>( );
        for (Category category1:queryCategoryList) {
            if(category1.getPid()== pid){
                tempList.add(category1);
                List<Category> sonList = treeCategory(queryCategoryList, category1.getId( ));
                if(sonList.size()>0){
                    category1.setSonList(sonList);
                }
            }
        }
        return tempList;
    }

    @Override
    public ServerResponse addCategory(Category category) {
        RedisUtils.delKey(Constant.REDIS_CATETORY_KEY);
        categoryMapper.addCategory(category);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse deleteCateIds(List<Integer> ids) {
        RedisUtils.delKey(Constant.REDIS_CATETORY_KEY);
        categoryMapper.deleteCateIds(ids);
        return ServerResponse.success();
    }
}
