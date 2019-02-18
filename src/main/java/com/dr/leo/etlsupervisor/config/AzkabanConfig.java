package com.dr.leo.etlsupervisor.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/18 15:20
 */
@Data
@Component
@PropertySource(value = {"classpath:/config/azkaban-config.properties"})
@ConfigurationProperties(prefix = "dr.azkaban")
public class AzkabanConfig {
    private String url;
    private String username;
    private String password;
    private Integer connectTimeOut;
    private Integer readTimeOut;
    private String contentType;
    private String xRequestWith;
    private Boolean postDoOutPut;
    private Boolean postDoInPut;
    private Boolean postUseCaches;
}
