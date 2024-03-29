package com.dr.leo.etlsupervisor.service.impl;

import cn.hutool.core.util.StrUtil;
import com.dr.leo.etlsupervisor.common.DateKit;
import com.dr.leo.etlsupervisor.entity.EtlSystemLog;
import com.dr.leo.etlsupervisor.params.EtlLogQueryParams;
import com.dr.leo.etlsupervisor.repository.EtlSystemLogRepository;
import com.dr.leo.etlsupervisor.service.EtlSystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/13 17:29
 */
@Service
public class EtlSystemLogServiceImpl implements EtlSystemLogService {
    private final EtlSystemLogRepository logRepository;

    @Autowired
    public EtlSystemLogServiceImpl(EtlSystemLogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public Page<EtlSystemLog> findAllLogs(EtlLogQueryParams queryParams) {
        final int currentPageNo = queryParams.getPageNo() > 0 ? queryParams.getPageNo() - 1 : 0;
        Pageable pageable = PageRequest.of(currentPageNo, queryParams.getPageSize(), Sort.Direction.ASC, "id");
        Specification<EtlSystemLog> specification = (Specification<EtlSystemLog>) (root, query, criteriaBuilder) -> {

            Predicate p1 = criteriaBuilder.gt(root.get("id"), 0);
            Predicate p2 = null;
            if (StrUtil.isNotBlank(queryParams.getPickDate())) {
                p2 = criteriaBuilder.between(root.get("logTime"),
                        queryParams.getPickDateTimeStart(), queryParams.getPickDateTimeEnd());
            }
            Predicate p3 = criteriaBuilder.equal(root.get("retailerCode"), queryParams.getKeyWords());
            Predicate p4 = criteriaBuilder.like(root.get("logLevel"), queryParams.getKeyWords());
            Predicate p5 = criteriaBuilder.like(root.get("logType"), "%" + queryParams.getKeyWords() + "%");

            if (StrUtil.isBlank(queryParams.getKeyWords()) && StrUtil.isBlank(queryParams.getPickDate())) {
                query.where(p1);
            } else if (StrUtil.isBlank(queryParams.getKeyWords()) && StrUtil.isNotBlank(queryParams.getPickDate())) {
                query.where(p2);
            } else if (StrUtil.isNotBlank(queryParams.getKeyWords()) && StrUtil.isBlank(queryParams.getPickDate())) {
                query.where(criteriaBuilder.or(p3, p4, p5));
            } else {
                query.where(criteriaBuilder.and(p2), criteriaBuilder.or(p3, p4, p5));
            }
            return query.getRestriction();
        };
        return logRepository.findAll(specification, pageable);
    }

    @Override
    public String logContentOfOneLevelAfterOneDate(String logLevel, LocalDateTime dateTime) {
        List<EtlSystemLog> logList = logRepository.findAllByLogLevelAndLogTimeAfter(logLevel, dateTime);
        if (logList.isEmpty()) {
            return "";
        }
        StringBuilder content = new StringBuilder();
        logList.forEach(etlSystemLog -> content.append(etlSystemLog.getRetailerCode())
                .append("\t").append(etlSystemLog.getMsg())
                .append("\t").append(etlSystemLog.getLogLevel())
                .append("\t").append(etlSystemLog.getLogType())
                .append("\t").append(DateKit.toDateTimeStr(etlSystemLog.getLogTime()))
                .append("\t").append(etlSystemLog.getWorker()).append("\n"));
        return content.toString();
    }
}
