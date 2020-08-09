package com.fh.order.biz;

import com.fh.cart.vo.Cart;
import com.fh.common.ServerResponse;
import com.fh.order.po.Order;
import com.fh.param.OrderParam;
import com.fh.vo.ProductVo;

import java.util.List;

public interface IOrderService {
    ServerResponse gengerOrder(OrderParam orderParam);


    void createOrder(Cart cart, OrderParam orderParam, List<ProductVo> productVoList);

    void updateOrder(Order order);
}
