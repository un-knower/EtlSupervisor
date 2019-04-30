package com.dr.leo.etlsupervisor.dialect;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/4/30 15:28
 */
@Configuration
public class DialectConfig {
    @Bean
    public ShowExecuteLengthTagDialect showExecuteLengthTagDialect() {
        return new ShowExecuteLengthTagDialect();
    }
}
