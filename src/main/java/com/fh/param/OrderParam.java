package com.fh.param;

import lombok.Data;

@Data
public class OrderParam {

    private Long receiverId;
    private Integer orderPayType;
    private Long memberId;
    private Long[] checkProductIds;
}
