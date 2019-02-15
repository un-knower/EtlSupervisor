package com.dr.leo.etlsupervisor.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/15 17:51
 */
@Data
@Component
@PropertySource(value = {"classpath:/config/email-config.properties"})
@ConfigurationProperties(prefix = "mail")
public class EmailConfig {
    private String from;
    private String to;
    private String subject;
}
