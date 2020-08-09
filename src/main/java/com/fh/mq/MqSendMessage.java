package com.fh.mq;

import com.fh.config.MqConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class MqSendMessage {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void handOrder(String data) {
        rabbitTemplate.convertAndSend(MqConfig.ORDEREXCHANGE, MqConfig.ORDERQUEUEKEY, data);
    }

}
