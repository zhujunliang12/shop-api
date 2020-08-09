package com.fh.cart.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fh.utils.BigDecimalSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {

    private Long cartId;
    private Integer totalNum;
    @JsonSerialize(using = BigDecimalSerialize.class)
    private BigDecimal totalPrice;
    private List<CartItem> cartItemList = new ArrayList<CartItem>( );
}
