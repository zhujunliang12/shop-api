package com.fh.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.cart.vo.Cart;
import com.fh.order.po.Order;
import com.fh.param.OrderParam;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface OrderMapper extends BaseMapper<Order> {

    void createOrder(Cart cart, OrderParam orderParam);

    @Update("update mall_product set stock=stock-#{num} where productId=#{goodsId} and stock>#{num}")
    int updateStock(@Param("goodsId") Long goodsId, @Param("num") int num);
}
