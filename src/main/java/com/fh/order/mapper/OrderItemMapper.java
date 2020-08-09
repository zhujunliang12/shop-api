package com.fh.order.mapper;

import com.alipay.api.domain.OrderItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.order.po.OrderTtem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderItemMapper extends BaseMapper<OrderItem> {


    void insertBath(@Param("list") List<OrderTtem> list);
}
