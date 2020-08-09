package com.fh.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Properties;

@Component
public class MailUtils {

    @Value("${mail_host}")
    private String mailHost;
    @Value("${mail_protocol}")
    private String smtp;
    @Value("${mail_name}")
    private String mailName;
    @Value("${mail_password}")
    private String mailPassword;

    /**
     * 发送邮件工具类
     *
     * @param address 邮件的收件人
     * @param title   邮件的标题
     * @param content 邮件的内容
     * @throws Exception
     */
    public void sendMail(String address, String title, String content) {
        Properties prop = new Properties( );
        prop.setProperty("mail.host", mailHost);
        prop.setProperty("mail.transport.protocol", smtp);
        prop.setProperty("mail.smtp.auth", "true");
        //1、创建session
        Session session = Session.getInstance(prop);
        //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        //session.setDebug(true);
        //2、通过session得到transport对象
        Transport ts = null;
        try {
            ts = session.getTransport( );
            //3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
            ts.connect(mailHost, mailName, mailPassword);
            MimeMessage message = new MimeMessage(session);
            //指明邮件的发件人
            message.setFrom(new InternetAddress(mailName));
            //指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(address));
            //邮件的标题
            message.setSubject(title);
            //邮件的文本内容
            message.setContent(content, "text/html;charset=UTF-8");
            //5、发送邮件
            ts.sendMessage(message, message.getAllRecipients( ));
        } catch (Exception e) {
            e.printStackTrace( );
        } finally {
            if (null != ts) {
                try {
                    ts.close( );
                } catch (MessagingException e) {
                    e.printStackTrace( );
                }
            }
        }
    }

    /**
     * 发送邮件 带附件 工具类
     *
     * @param from    邮件的发件人
     * @param address 邮件的收件人
     * @param title   邮件的标题
     * @param content 邮件的内容
     * @throws Exception
     */
    public static void sendMail(String from, String address, String title, String content, String path) {
        Properties prop = new Properties( );
        prop.setProperty("mail.host", "smtp.qq.com");
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");
        //1、创建session
        Session session = Session.getInstance(prop);
        //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        //session.setDebug(true);
        //2、通过session得到transport对象
        Transport ts = null;
        try {
            ts = session.getTransport( );
            //3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
            ts.connect("smtp.qq.com", from, "ozfankizfsdcbjjh");
            MimeMessage message = new MimeMessage(session);
            //指明邮件的发件人
            message.setFrom(new InternetAddress(from));
            //指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(address));
            //邮件的标题
            message.setSubject(title);

            //创建邮件附件
            MimeBodyPart text = new MimeBodyPart( );
            //邮件的文本内容
            text.setContent(content, "text/html;charset=UTF-8");

            MimeBodyPart attach = new MimeBodyPart( );
            DataHandler dh = new DataHandler(new FileDataSource(path));
            attach.setDataHandler(dh);
            attach.setFileName(dh.getName( ));

            //创建容器描述数据关系
            MimeMultipart mp = new MimeMultipart( );
            mp.addBodyPart(text);
            mp.addBodyPart(attach);
            mp.setSubType("mixed");
            message.setContent(mp);
            //5、发送邮件
            ts.sendMessage(message, message.getAllRecipients( ));
        } catch (Exception e) {
            e.printStackTrace( );
        } finally {
            if (null != ts) {
                try {
                    ts.close( );
                } catch (MessagingException e) {
                    e.printStackTrace( );
                }
            }
        }
    }


}
