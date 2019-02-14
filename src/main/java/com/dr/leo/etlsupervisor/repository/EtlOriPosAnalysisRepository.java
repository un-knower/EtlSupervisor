package com.dr.leo.etlsupervisor.repository;

import com.dr.leo.etlsupervisor.entity.EtlOriPosAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/13 14:07
 */
@Repository
public interface EtlOriPosAnalysisRepository extends JpaRepository<EtlOriPosAnalysis, Integer> {
    /**
     * 根据零售商code查询所有零售商流水分析结果
     *
     * @param retailerCode 零售商code
     * @return 流水分析结果
     */
    List<EtlOriPosAnalysis> findAllByRetailerCodeOrderByRealDataDayAsc(String retailerCode);
}
