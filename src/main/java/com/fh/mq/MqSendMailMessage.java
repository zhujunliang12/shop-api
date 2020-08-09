package com.fh.mq;

import com.alibaba.fastjson.JSON;
import com.fh.config.MqConfig;
import com.fh.vo.MailMessageVo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MqSendMailMessage {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送 邮件到 中间消息间的方法
     *
     * @param mailMessageVo
     */
    public void sendMailMessage(MailMessageVo mailMessageVo) {
        String mailJson = JSON.toJSONString(mailMessageVo);
        rabbitTemplate.convertAndSend(MqConfig.MAILFANOUTEXCHANGE, "", mailJson);
    }


    public void sendMail(String info) {

        rabbitTemplate.convertAndSend("mailRotueKey", info);
    }


}
