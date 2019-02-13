package com.dr.leo.etlsupervisor.service;

import com.dr.leo.etlsupervisor.entity.EtlSystemLog;
import com.dr.leo.etlsupervisor.params.EtlLogQueryParams;
import org.springframework.data.domain.Page;

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
}
