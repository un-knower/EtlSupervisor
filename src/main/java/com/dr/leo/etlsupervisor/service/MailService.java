package com.dr.leo.etlsupervisor.service;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/15 17:12
 */
public interface MailService {
    /**
     * 发送html的邮件
     *
     * @param tos     收件人列表
     * @param subject 邮件主题
     * @param content 邮件正文
     * @throws Exception
     */
    void sendHtmlMail(String[] tos, String subject, String content) throws Exception;
}
