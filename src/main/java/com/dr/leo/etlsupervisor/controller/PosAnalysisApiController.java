package com.dr.leo.etlsupervisor.controller;

import com.dr.leo.etlsupervisor.common.RestResponseResult;
import com.dr.leo.etlsupervisor.entity.EtlOriPosAnalysis;
import com.dr.leo.etlsupervisor.service.impl.EtlOriPosAnalysisServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/13 14:12
 */
@RestController
@RequestMapping("/api/v1/pos")
public class PosAnalysisApiController {
    private final EtlOriPosAnalysisServiceImpl posAnalysisService;

    @Autowired
    public PosAnalysisApiController(EtlOriPosAnalysisServiceImpl posAnalysisService) {
        this.posAnalysisService = posAnalysisService;
    }

    @GetMapping("/showAllAnalysis")
    public RestResponseResult showAllAnalysis() throws Exception {
        List<EtlOriPosAnalysis> posAnalysisList = posAnalysisService.showAllPosAnalysis();
        return RestResponseResult.ok(posAnalysisList);
    }

}
