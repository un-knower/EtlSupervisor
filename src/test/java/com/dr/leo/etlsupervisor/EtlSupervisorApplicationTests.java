package com.dr.leo.etlsupervisor;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.dr.leo.etlsupervisor.common.Pageable;
import com.dr.leo.etlsupervisor.service.impl.MailServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Arrays;
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
        Pageable pageable = new Pageable(10, 101);

        System.out.println(pageable.getPages());
        System.out.println(pageable.getStartRow());
        System.out.println(pageable.getEndRow());
        System.out.println(pageable.getItems());
        System.out.println(pageable.getNextPage(3));
        System.out.println(pageable.getPreviousPage(3));
        System.out.println(pageable.getLastPage());
        System.out.println(pageable.getFirstPage());
        System.out.println(pageable);
        int [] slider = pageable.getSlider(10);
        System.out.println("342342342");
        /*System.out.println(1957%60);
        Long totalSeconds = 9157570L / 1000;
        Long hour = totalSeconds / 3600;
        Long seconds = totalSeconds % 3600;
        Long min = seconds / 60;
        Long second = seconds % 60;
        System.out.println(StrUtil.format("{}-{}-{}", hour, min, second));*/
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

