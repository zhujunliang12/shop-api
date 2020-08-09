package com.fh.receiverInfo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "mall_orderInfo")
public class ReceiverInfo implements Serializable {

    private Long receiverId;
    private String receiverName; //收件人
    private String address;   //收件人地址
    private String receiverPhone;  //收件人的手机号
    private String mail;      //收件人地址
    private String adressName;  // 现住地址名
    private Long id;    // 会员id
    private Integer statu;   // 0代表 默认 1 代表不选

}
