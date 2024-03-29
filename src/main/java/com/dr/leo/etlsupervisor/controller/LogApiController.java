package com.dr.leo.etlsupervisor.controller;

import com.dr.leo.etlsupervisor.common.RestResponseResult;
import com.dr.leo.etlsupervisor.entity.EtlSystemLog;
import com.dr.leo.etlsupervisor.params.EtlLogQueryParams;
import com.dr.leo.etlsupervisor.service.impl.EtlSystemLogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/13 18:20
 */
@RestController
@RequestMapping("/api/v1/logs")
public class LogApiController {
    private final EtlSystemLogServiceImpl logService;

    @Autowired
    public LogApiController(EtlSystemLogServiceImpl logService) {
        this.logService = logService;
    }

    @GetMapping("/showAllLogs")
    public RestResponseResult showAllLogs(EtlLogQueryParams params) {
        Page<EtlSystemLog> logs = logService.findAllLogs(params);
        return RestResponseResult.ok(logs);
    }
}
