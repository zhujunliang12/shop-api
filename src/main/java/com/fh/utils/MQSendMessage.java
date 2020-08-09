package com.fh.utils;

import com.alibaba.fastjson.JSON;
import com.fh.brand.po.Brand;
import com.fh.product.po.Product;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springfox.documentation.spring.web.json.Json;

@Component
public class MQSendMessage {

    @Autowired
    private AmqpTemplate amqpTemplate;


    public void sendProductMessage(Product product) {
        String s = JSON.toJSONString(product);
        amqpTemplate.convertAndSend("productExchage", "product", s);
    }

    //默认的 交换机
    public void sendBrandMessage(Brand brand) {
        String s = JSON.toJSONString(brand);
        amqpTemplate.convertAndSend("brand-queue", s);
    }


    public void sendMailMessage(String info) {
        amqpTemplate.convertAndSend("mailExchage", "mailRotueKey", info);
    }

}
