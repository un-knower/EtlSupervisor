package com.dr.leo.etlsupervisor.service;

import com.dr.leo.etlsupervisor.entity.EtlOriPosAnalysis;

import java.util.List;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/13 14:08
 */
public interface EtlOriPosAnalysisService {
    /**
     * 展示所有流水分析
     *
     * @return 所有流水分析结果
     */
    List<EtlOriPosAnalysis> showAllPosAnalysis();
}
