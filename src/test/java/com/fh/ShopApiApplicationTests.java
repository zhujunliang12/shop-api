package com.fh;

import com.fh.brand.po.Brand;
import com.fh.member.biz.SendMail;
import com.fh.product.po.Product;
import com.fh.utils.MQSendMessage;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import java.math.BigDecimal;

@SpringBootTest
@RunWith(SpringRunner.class)
class ShopApiApplicationTests {

    @Autowired
    private SendMail mailService;
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MQSendMessage mqSendMessage;


    @Autowired
    private RabbitTemplate rabbitTemplate;  // 注入一个RabbitMQ的模板对象，操作消息队列的对象


    private String toEmail = "532028476@qq.com";        // 填写你的qq邮箱号

    @Test
    void contextLoads() {
    }

    @Test
    public void sendTextMail() {
        mailService.sendTextMail(toEmail, "朱俊亮", "测试");
    }

    @Test
    public void sendAttachmentMail() {
        mailService.sendAttachmentMail(toEmail, "这是带附件的邮件", "有附件", "D:\\log.log");
    }


    @Test
    public void testSend() throws MessagingException {
        SimpleMailMessage message = new SimpleMailMessage( );
        message.setFrom("1179363482@qq.com");
        message.setTo("260006856@qq.com");
        message.setSubject("这是标题");
        message.setText("这是内容");
        javaMailSender.send(message);
    }


    /**
     * 测试 rabit 发送信息
     */
    @Test
    public void mqSendProductMessage() {
        for (int i = 0; i <= 10; i++) {
            Product product = new Product( );
            product.setId(i);
            product.setPrice(BigDecimal.valueOf(33.3));
            product.setName("sds");
            mqSendMessage.sendProductMessage(product);
        }
    }

    /**
     * 测试 默认的 交换机 发送信息
     */
    @Test
    public void mqSendBrandMessage() {
        Brand brand = new Brand( );
        brand.setBrandId(1);
        brand.setName("xiaomingn");
        mqSendMessage.sendBrandMessage(brand);
    }

    @Test
    public void mqSendMail() {
        mqSendMessage.sendMailMessage("你好ssss");
    }


}
