package com.dr.leo.etlsupervisor.service.impl;

import cn.hutool.core.util.StrUtil;
import com.dr.leo.etlsupervisor.config.EmailConfig;
import com.dr.leo.etlsupervisor.exception.ServiceException;
import com.dr.leo.etlsupervisor.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeUtility;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/15 17:13
 */
@Service
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;

    private final EmailConfig emailConfig;

    @Autowired
    public MailServiceImpl(JavaMailSender mailSender, EmailConfig emailConfig) {
        this.mailSender = mailSender;
        this.emailConfig = emailConfig;
    }

    @Override
    public void sendHtmlMail(String[] tos, String subject, String content) throws Exception {
        if (null == tos || tos.length == 0) {
            throw new ServiceException("收件人列表不能为空！");
        }
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(emailConfig.getFrom());
        mailMessage.setTo(tos);
        String subjectUtf8 = MimeUtility.encodeWord(subject, "UTF-8", "Q");
        mailMessage.setSubject(subjectUtf8);
        mailMessage.setText(content);
        mailSender.send(mailMessage);
        System.out.println("success");
    }

    public void sendHtmlMail(String content) throws Exception {
        if (StrUtil.isBlank(emailConfig.getTo())) {
            throw new ServiceException("收件人列表不能为空！");
        }
        String[] tos = emailConfig.getTo().split(",");
        sendHtmlMail(tos, emailConfig.getSubject(), content);
    }

}
