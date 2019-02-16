package com.dr.leo.etlsupervisor.task;

import cn.hutool.core.util.StrUtil;
import com.dr.leo.etlsupervisor.common.DateKit;
import com.dr.leo.etlsupervisor.common.LogLevelEnum;
import com.dr.leo.etlsupervisor.service.impl.EtlSystemLogServiceImpl;
import com.dr.leo.etlsupervisor.service.impl.MailServiceImpl;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/15 21:12
 */
@DisallowConcurrentExecution
public class EmailSendTask extends QuartzJobBean {
    @Resource
    private EtlSystemLogServiceImpl logService;
    @Resource
    private MailServiceImpl mailService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String dangerLogContent = logService.logContentOfOneLevelAfterOneDate(LogLevelEnum.WARN.getLogLevel(),
                DateKit.toLocalDateTime(DateKit.yesterday() + " 00:00:00"));
        if (StrUtil.isBlank(dangerLogContent)) {
            return;
        }
        try {
            mailService.sendHtmlMail(dangerLogContent);
            Thread.sleep(6000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
