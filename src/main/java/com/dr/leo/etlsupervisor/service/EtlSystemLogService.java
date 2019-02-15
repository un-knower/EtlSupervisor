package com.dr.leo.etlsupervisor.service;

import com.dr.leo.etlsupervisor.entity.EtlSystemLog;
import com.dr.leo.etlsupervisor.params.EtlLogQueryParams;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/13 17:29
 */
public interface EtlSystemLogService {
    /**
     * 分页查询日志
     *
     * @param queryParams 日志查询参数对象
     * @return 查询结果
     */
    Page<EtlSystemLog> findAllLogs(EtlLogQueryParams queryParams);

    /**
     * 查询某一日期之后，某一级别的所有日志内容
     *
     * @param logLevel 日志级别
     * @param localDateTime     日期
     * @return 日志内容
     */
    String logContentOfOneLevelAfterOneDate(String logLevel, LocalDateTime localDateTime);
}
