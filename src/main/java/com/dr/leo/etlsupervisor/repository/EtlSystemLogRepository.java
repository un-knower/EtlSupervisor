package com.dr.leo.etlsupervisor.repository;

import com.dr.leo.etlsupervisor.entity.EtlSystemLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/13 17:28
 */
@Repository
public interface EtlSystemLogRepository extends JpaRepository<EtlSystemLog, Integer>,
        JpaSpecificationExecutor<EtlSystemLog> {
}
