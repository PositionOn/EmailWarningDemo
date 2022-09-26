package com.example.emailwarningdemo.Utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class EmailServiceImpl  implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    public Configuration configuration;

    @Value("${spring.mail.username}")
    public String USER_NAME;//发送者

    static {
        System.setProperty("mail.mime.splitlongparameters","false");
    }


    @Override
    public void send(Email mail) {
        try {
            log.info("发送普通文本邮件：{}", mail.getContent());
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(USER_NAME);
            message.setTo(mail.getEmail());
            message.setSubject(mail.getSubject());
            message.setText(mail.getContent());
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendFreemarker(Email mail) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            //这里可以自定义发信名称比如：工作流
            helper.setFrom(USER_NAME, "系统邮件通知");
            helper.setTo(mail.getEmail());
            helper.setSubject(mail.getSubject());
            Map<String, Object> model = new HashMap<>();
            model.put("mail", mail);
            model.put("exception", mail.getKvMap());
            Template template = configuration.getTemplate(mail.getTemplate());
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            helper.setText(text, true);
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
