package com.dr.leo.etlsupervisor.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.dr.leo.etlsupervisor.common.RestResponseResult;
import com.dr.leo.etlsupervisor.entity.EtlOriPosAnalysis;
import com.dr.leo.etlsupervisor.service.impl.EtlOriPosAnalysisServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/13 14:12
 */
@RestController
@RequestMapping("/api/v1/pos")
public class PosAnalysisApiController {
    private final EtlOriPosAnalysisServiceImpl posAnalysisService;

    private final static String USEFUL_POS_TAG = "1";
    private final static String NOT_USEFUL_POS_TAG = "0";
    private final static String FINAL_POS_TAG = "2";
    private final static int POS_ANALYSIS_LIMIT = 30;

    @Autowired
    public PosAnalysisApiController(EtlOriPosAnalysisServiceImpl posAnalysisService) {
        this.posAnalysisService = posAnalysisService;
    }

    @GetMapping("/showAllAnalysis")
    public RestResponseResult showAllAnalysis(String retailerCode) {
        Map<String, Object> data = CollectionUtil.newHashMap(5);
        Map<Date, List<EtlOriPosAnalysis>> allPosAnalysisOfRetailerMap =
                posAnalysisService.showAllPosAnalysisOfRetailerMap(retailerCode);
        if (null == allPosAnalysisOfRetailerMap) {
            return RestResponseResult.ok(data, 40005);
        }
        List<Date> dateList = new ArrayList<>(allPosAnalysisOfRetailerMap.keySet());
        List<Integer> usefulCountList = new ArrayList<>();
        List<Integer> notUsefulCountList = new ArrayList<>();
        List<Integer> finalPosCountList = new ArrayList<>();
        Collections.sort(dateList);
        if (dateList.size() > POS_ANALYSIS_LIMIT) {
            dateList = dateList.subList(dateList.size() - POS_ANALYSIS_LIMIT, dateList.size() - 1);
        }
        dateList.forEach(date -> {
            List<EtlOriPosAnalysis> etlOriPosAnalyses = allPosAnalysisOfRetailerMap.get(date);
            etlOriPosAnalyses.forEach(posAnalysis -> {
                if (USEFUL_POS_TAG.equals(posAnalysis.getTag())) {
                    usefulCountList.add(posAnalysis.getTotalFlowCount());
                } else if (NOT_USEFUL_POS_TAG.equals(posAnalysis.getTag())) {
                    notUsefulCountList.add(posAnalysis.getTotalFlowCount());
                } else if (FINAL_POS_TAG.equals(posAnalysis.getTag())) {
                    finalPosCountList.add(posAnalysis.getTotalFlowCount());
                }
            });
        });
        data.put("dateList", dateList);
        data.put("usefulCountList", usefulCountList);
        data.put("notUsefulCountList", notUsefulCountList);
        data.put("finalPosCountList", finalPosCountList);
        return RestResponseResult.ok(data);
    }

}
