package com.dr.leo.etlsupervisor.repository;

import com.dr.leo.etlsupervisor.entity.EtlSystemLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/13 17:28
 */
@Repository
public interface EtlSystemLogRepository extends JpaRepository<EtlSystemLog, Integer>,
        JpaSpecificationExecutor<EtlSystemLog> {
    /**
     * 根据日志级别，日期查询所有日志
     *
     * @param logLevel 日志级别
     * @param dateTime     日期
     * @return 所有危险日志
     */
    List<EtlSystemLog> findAllByLogLevelAndLogTimeAfter(String logLevel, LocalDateTime dateTime);
}
