package com.fh.order.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fh.utils.BigDecimalSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@TableName(value = "mall_order_item")
public class OrderTtem implements Serializable {

    private Integer id;

    private String orderId;

    private Long memberId;

    private String goodsName;
    @JsonSerialize(using = BigDecimalSerialize.class)
    private BigDecimal price;
    private int num;
    @JsonSerialize(using = BigDecimalSerialize.class)
    private BigDecimal subPrice;
    private String imgUrl;
}
