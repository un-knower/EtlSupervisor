package com.dr.leo.etlsupervisor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.spring5.dialect.SpringStandardDialect;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/15 10:20
 */
@Configuration
public class TemplateConfig {
    @Bean(name = "stringTemplateEngine")
    public TemplateEngine springTemplateEngine() {
        TemplateEngine templateEngine = new TemplateEngine();
        IDialect springStandardDialect = new SpringStandardDialect();
        templateEngine.setDialect(springStandardDialect);
        ClassLoaderTemplateResolver loaderTemplateResolver = new ClassLoaderTemplateResolver();
        loaderTemplateResolver.setTemplateMode(TemplateMode.TEXT);
        loaderTemplateResolver.setPrefix("/templates/");
        loaderTemplateResolver.setSuffix(".job");
        loaderTemplateResolver.setCacheTTLMs(360000L);
        loaderTemplateResolver.setCacheable(true);
        templateEngine.setTemplateResolver(loaderTemplateResolver);
        return templateEngine;
    }
}
