package com.fh.order.po;

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
@TableName(value = "mall_order")
public class Order implements Serializable {

    @TableId(type = IdType.INPUT)
    private String orderId; // 雪花算法的id

    private Long memberId; // 会员id

    private Long receiverId; //收件人呢id

    private Date createDate; //订单创建时间

    private Integer orderPayType;// 支付方式  0代表微信 1代表支付宝 2 代表其他

    private Date payTime;  //订单支付时间

    private Integer orderStatu;   //订单状态
    @JsonSerialize(using = BigDecimalSerialize.class)
    private BigDecimal totalPrice;  //总价格

    private Integer totalNum;    //总数量

    private Date faHuoDate;    //货物发货时间

    private String receiverName; //收件人
    private String address;   //收件人地址
    private String receiverPhone;  //收件人的手机号


}
