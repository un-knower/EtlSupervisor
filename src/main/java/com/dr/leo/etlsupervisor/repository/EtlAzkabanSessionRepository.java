package com.dr.leo.etlsupervisor.repository;

import com.dr.leo.etlsupervisor.entity.EtlAzkabanSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/18 15:40
 */
@Repository
public interface EtlAzkabanSessionRepository extends JpaRepository<EtlAzkabanSession, String> {
}
