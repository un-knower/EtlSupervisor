package com.dr.leo.etlsupervisor.service.impl;

import com.dr.leo.etlsupervisor.entity.EtlOriPosAnalysis;
import com.dr.leo.etlsupervisor.repository.EtlOriPosAnalysisRepository;
import com.dr.leo.etlsupervisor.service.EtlOriPosAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/13 14:10
 */
@Service
public class EtlOriPosAnalysisServiceImpl implements EtlOriPosAnalysisService {
    private final EtlOriPosAnalysisRepository posAnalysisRepository;

    @Autowired
    public EtlOriPosAnalysisServiceImpl(EtlOriPosAnalysisRepository posAnalysisRepository) {
        this.posAnalysisRepository = posAnalysisRepository;
    }

    @Override
    public List<EtlOriPosAnalysis> showAllPosAnalysis() {
        return posAnalysisRepository.findAll();
    }
}
