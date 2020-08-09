package com.fh.pay.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fh.utils.BigDecimalSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName(value = "mall_paylog")
public class PayLog implements Serializable {

    @TableId(value = "out_trade_no", type = IdType.INPUT)
    private String out_trade_no;   //支付日志id
    private Long memberId; //会员id
    private String orderId;
    private Date payTime; //支付时间
    private Date payCreateTime;  //订单创建时间
    private Integer payStatu; // 支付状态  0 未支付  1 已支付
    private Integer payType;  //支付方式  0微信支付  1 支付宝支付 2 其他
    @JsonSerialize(using = BigDecimalSerialize.class)
    private BigDecimal payTotalPrice;
    private String transactionId;   //调用微信成功 后 返回的

}
