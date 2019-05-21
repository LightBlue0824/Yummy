package edu.nju.yummy.util;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Date;
import java.util.Properties;

public class MailUtil {
    private static Session mailSession = null;

    private static Session getMailSession(){
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", "smtp.nju.edu.cn");
        properties.put("mail.smtp.auth", "true");
        Session mailSession = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("161250181@smail.nju.edu.cn", "Yyr19980711");
            }
        });

        return mailSession;
    }

    public static int sendMail(String to, String message){
        if (mailSession == null){
            mailSession = getMailSession();
            if(mailSession == null){
                return 0;       //获取邮件服务器失败
            }
        }

        try{
            MimeMessage msg = new MimeMessage(mailSession);
            InternetAddress dest = new InternetAddress(to);
            msg.setFrom(new InternetAddress("161250181@smail.nju.edu.cn"));
            msg.setSubject("注册成功-Yummy!", "gbk");
            msg.setRecipient(Message.RecipientType.TO, dest);
            msg.setSentDate(new Date());
            msg.setContent(message, "text/html; charset=gbk");

            Transport.send(msg);
            System.out.println("发送成功");
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return 1;
    }
}
