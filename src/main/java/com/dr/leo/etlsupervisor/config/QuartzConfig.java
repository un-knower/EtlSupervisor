package com.dr.leo.etlsupervisor.config;

import com.dr.leo.etlsupervisor.task.EmailSendTask;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/15 21:14
 */
@Configuration
public class QuartzConfig {
    @Bean
    public JobDetail emailSendTaskDetail() {
        return JobBuilder.newJob(EmailSendTask.class).withIdentity("emailSendTask")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger emailSendTrigger() {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 */5 * * * ?");
        return TriggerBuilder.newTrigger().forJob(emailSendTaskDetail())
                .withIdentity("emailSendTask")
                .withSchedule(scheduleBuilder)
                .build();
    }
}
