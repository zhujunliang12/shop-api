package com.fh.mq;

import com.alibaba.fastjson.JSONObject;
import com.fh.common.MailUtils;
import com.fh.config.MqConfig;
import com.fh.vo.MailMessageVo;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MqMailReciver {

    @Autowired
    private MailUtils mailUtils;

   /* @RabbitListener(queues="mail-queue")
    public void handerMailMessage(String mailJson){
        MailMessageVo mailMessageVo = JSONObject.parseObject(mailJson, MailMessageVo.class);
        System.out.println(mailMessageVo.getRealName()+"==============");
        String loginTime = mailMessageVo.getLoginTime( );
        mailMessageVo.setMailTo("260006856@qq.com");
        String mailTo = mailMessageVo.getMailTo( );
        String realName = mailMessageVo.getRealName( );
        String uuid = mailMessageVo.getUuid( );
        mailUtils.sendMail(mailTo,"成功发送",realName+"在"+loginTime+"登录成功");
    }*/

    //手动 删除 ack
    @RabbitListener(queues = MqConfig.MAILQUEUE)
    public void handerMailMessage(String mailJson, Message message, Channel channel) throws IOException {
        MessageProperties messageProperties = message.getMessageProperties( );
        long deliveryTag = messageProperties.getDeliveryTag( );
        try {
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            e.printStackTrace( );
            //  丢弃的意思  我们可以 将丢弃的消息 放入到 死信队列中
            channel.basicNack(deliveryTag, false, false);
        }
        /*String loginTime = mailMessageVo.getLoginTime( );
        mailMessageVo.setMailTo("260006856@qq.com");
        String mailTo = mailMessageVo.getMailTo( );
        String realName = mailMessageVo.getRealName( );
        String uuid = mailMessageVo.getUuid( );
        mailUtils.sendMail(mailTo,"成功发送",realName+"在"+loginTime+"登录成功");*/
    }


}
