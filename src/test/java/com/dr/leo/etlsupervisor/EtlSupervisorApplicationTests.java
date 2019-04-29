package com.dr.leo.etlsupervisor;

import com.dr.leo.etlsupervisor.service.impl.MailServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EtlSupervisorApplicationTests {
    @Qualifier("stringTemplateEngine")
    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private MailServiceImpl mailService;

    @Test
    public void contextLoads() {
        Context context = new Context();
        User user = new User();
        user.setName("leo");
        context.setVariable("user", user);
        System.out.println(templateEngine.process("plugin/test", context));
    }

    @Test
    public void testSendEmail() {
        try {
            mailService.sendHtmlMail("邮件内容");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUser() {
        System.out.println("434: " + getFileTag("bigdata_buy2_2019_04_27.DAT"));

    }

    private String getFileTag(String fileName) {
        String regex = "(bigdata_[A-Za-z]+)";
        String result = "";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fileName);
        while (matcher.find()) {
            result = matcher.group(1);
        }
        return result;
    }

}

class User {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

