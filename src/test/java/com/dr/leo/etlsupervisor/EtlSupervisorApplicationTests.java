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

