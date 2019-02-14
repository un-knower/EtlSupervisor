package com.dr.leo.etlsupervisor.repository;

import com.dr.leo.etlsupervisor.entity.EtlRetailerConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/14 15:26
 */
@Repository
public interface EtlRetailerConfigRepository extends JpaRepository<EtlRetailerConfig, String> {
}
