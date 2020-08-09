package com.fh.cart.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fh.utils.BigDecimalSerialize;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItem {


    private Long goodsId;
    private String goodsName;
    @JsonSerialize(using = BigDecimalSerialize.class)
    private BigDecimal price;
    private int num;
    @JsonSerialize(using = BigDecimalSerialize.class)
    private BigDecimal subPrice;
    private String imgUrl;

    private int attention;  // 0 是不关注 1 是关注

}
