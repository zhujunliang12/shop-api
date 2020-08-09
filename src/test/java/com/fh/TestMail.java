package com.fh;

import org.junit.Test;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.FileOutputStream;
import java.util.Properties;

public class TestMail {

    /**
     * 测试发送邮件   没有测试其他类中的方法 所以用一般的测试类就可以了
     */
    @Test
    public void testSendMail() throws Exception {
        Properties prop = new Properties( );
        prop.setProperty("mail.host", "smtp.qq.com");
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");
        //使用JavaMail发送邮件的5个步骤
        //1、创建session
        Session session = Session.getInstance(prop);
        //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        //session.setDebug(true);
        //2、通过session得到transport对象
        Transport ts = session.getTransport( );
        //3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
        ts.connect("smtp.qq.com", "260006856@qq.com", "ozfankizfsdcbjjh");
        //4、创建邮件
        Message message = createSimpleMail(session);
        // 发送邮件带附件
        //Message message = createAttachMail(session);
        //5、发送邮件
        ts.sendMessage(message, message.getAllRecipients( ));
        ts.close( );
    }

    /**
     * @param session
     * @return
     * @throws Exception
     * @Method: createSimpleMail
     * @Description: 创建一封只包含文本的邮件
     * @Anthor:孤傲苍狼
     */
    public static MimeMessage createSimpleMail(Session session)
            throws Exception {
        //创建邮件对象
        MimeMessage message = new MimeMessage(session);
        //指明邮件的发件人
        message.setFrom(new InternetAddress("260006856@qq.com"));
        //指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("260006856@qq.com"));
        //邮件的标题
        message.setSubject("只包含文本的简单邮件");
        //邮件的文本内容
        message.setContent("你好啊！", "text/html;charset=UTF-8");
        //返回创建好的邮件对象
        return message;
    }


    /**
     * @param session
     * @return
     * @throws Exception
     * @Method: createAttachMail
     * @Description: 创建一封带附件的邮件
     * @Anthor:孤傲苍狼
     */
    public static MimeMessage createAttachMail(Session session) throws Exception {
        MimeMessage message = new MimeMessage(session);

        //设置邮件的基本信息
        //发件人
        message.setFrom(new InternetAddress("260006856@qq.com"));
        //收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("260006856@qq.com"));
        //邮件标题
        message.setSubject("JavaMail邮件发送测试");

        //创建邮件正文，为了避免邮件正文中文乱码问题，需要使用charset=UTF-8指明字符编码
        MimeBodyPart text = new MimeBodyPart( );
        text.setContent("使用JavaMail创建的带附件的邮件", "text/html;charset=UTF-8");

        //创建邮件附件
        MimeBodyPart attach = new MimeBodyPart( );
        DataHandler dh = new DataHandler(new FileDataSource("d:\\微信图片_20190917194324.jpg"));
        attach.setDataHandler(dh);
        attach.setFileName(dh.getName( ));  //

        //创建容器描述数据关系
        MimeMultipart mp = new MimeMultipart( );
        mp.addBodyPart(text);
        mp.addBodyPart(attach);
        mp.setSubType("mixed");

        message.setContent(mp);
        message.saveChanges( );
        //将创建的Email写入到E盘存储
        // message.writeTo(new FileOutputStream("d:\\log"));
        //返回生成的邮件
        return message;
    }

}
