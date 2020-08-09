package com.fh.config;


import org.apache.poi.ss.formula.functions.T;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class MqConfig {

    public static final String PRODUCT_DELEHANGE = "productDLXExChange";
    public static final String PRODUCTEXCHAGE = "productExchage";
    public static final String PRODUCTQUEUE = "product-queue";
    public static final String PRODUCT = "product";
    public static final String PRODUCTDLXQUEUE = "product-dlx-queue";
    public static final String BRANDQUEUE = "brand-queue";
    public static final String MAILFANOUTEXCHANGE = "mailFanoutExchange";
    public static final String MAILQUEUE = "mail-queue";


    public static final String ORDEREXCHANGE = "orderExchange";
    public static final String ORDERQUEUE = "orderQueue";
    public static final String ORDERQUEUEKEY = "orderQueueKey";

    @Bean
    public DirectExchange productExchage() {
        return new DirectExchange(PRODUCTEXCHAGE, true, false);
    }

    @Bean
    public Queue productQueue() {
        HashMap<String, Object> res = new HashMap<>( );
        res.put("x-message-ttl", 5000);
        res.put("x-dead-letter-exchange", PRODUCT_DELEHANGE);
        return new Queue(PRODUCTQUEUE, true, false, false, res);
    }

    @Bean
    public Binding productBinding() {
        return BindingBuilder.bind(productQueue( )).to(productExchage( )).with(PRODUCT);
    }

    // 死信交换机  topicExchange
    @Bean
    public TopicExchange productDLXExChange() {
        return new TopicExchange(PRODUCT_DELEHANGE, true, false);
    }

    @Bean
    public Queue productDlXQueue() {
        return new Queue(PRODUCTDLXQUEUE, true);
    }

    @Bean
    public Binding productDLXBing() {
        return BindingBuilder.bind(productDlXQueue( )).to(productDLXExChange( )).with("#");
    }

    // 默认的 交换机
    @Bean
    public Queue brandQueue() {
        return new Queue(BRANDQUEUE, true);
    }

    // 订单的交换机
    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange(ORDEREXCHANGE, true, false);
    }

    // 订单的队列
    @Bean
    public Queue orderQueue() {
        return new Queue(ORDERQUEUE, true);
    }

    // 订单队列关联
    @Bean
    public Binding orderBulid() {
        return BindingBuilder.bind(orderQueue( )).to(orderExchange( )).with(ORDERQUEUEKEY);
    }


    @Bean
    public FanoutExchange mailFanoutExchange() {
        return new FanoutExchange(MAILFANOUTEXCHANGE, true, false);
    }

    @Bean
    public Queue mailQueue() {
        return new Queue(MAILQUEUE, true);
    }

    @Bean
    public Binding mailBinding() {
        return BindingBuilder.bind(mailQueue( )).to(mailFanoutExchange( ));
    }


    //以下是练习做的
    @Bean
    public DirectExchange mailExchage22() {
        return new DirectExchange("mailExchage", true, false);
    }

    @Bean
    public Queue mailQueue22() {
        HashMap<String, Object> res = new HashMap<>( );
        return new Queue("mailQueue", true);
    }

    @Bean
    public Binding mailBinding22() {
        return BindingBuilder.bind(mailQueue22( )).to(mailExchage22( )).with("mailRotueKey");
    }


}
