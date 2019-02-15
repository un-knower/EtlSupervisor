package com.dr.leo.etlsupervisor.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.dr.leo.etlsupervisor.entity.EtlOriPosAnalysis;
import com.dr.leo.etlsupervisor.repository.EtlOriPosAnalysisRepository;
import com.dr.leo.etlsupervisor.service.EtlOriPosAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/13 14:10
 */
@Service
public class EtlOriPosAnalysisServiceImpl implements EtlOriPosAnalysisService {
    private static final int TOTAL_ANALYSIS_NUM = 3;
    private static final List<String> TAGS_OF_ORI = CollectionUtil.newArrayList("1", "0", "2");
    private final EtlOriPosAnalysisRepository posAnalysisRepository;

    @Autowired
    public EtlOriPosAnalysisServiceImpl(EtlOriPosAnalysisRepository posAnalysisRepository) {
        this.posAnalysisRepository = posAnalysisRepository;
    }

    @Override
    public List<EtlOriPosAnalysis> showAllPosAnalysisOfRetailer(String retailerCode) {
        return posAnalysisRepository.findAllByRetailerCodeOrderByRealDataDayAsc(retailerCode);
    }

    public Map<java.sql.Date, List<EtlOriPosAnalysis>> showAllPosAnalysisOfRetailerMap(String retailerCode) {
        List<EtlOriPosAnalysis> posAnalysisList = showAllPosAnalysisOfRetailer(retailerCode);
        if (posAnalysisList.isEmpty()) {
            return null;
        }
        final Date startDate = posAnalysisList.get(0).getRealDataDay();
        final Date endDate = posAnalysisList.get(posAnalysisList.size() - 1).getRealDataDay();
        final List<java.sql.Date> dateList = DateUtil.rangeToList(startDate, endDate, DateField.DAY_OF_MONTH)
                .stream().map(x -> DateUtil.date(x).toSqlDate()).collect(Collectors.toList());
        final Map<java.sql.Date, List<EtlOriPosAnalysis>> posAnalysisMap = posAnalysisList.stream()
                .collect(Collectors.groupingBy(EtlOriPosAnalysis::getRealDataDay));
        dateList.forEach(date -> {
            List<EtlOriPosAnalysis> posAnalyses = posAnalysisMap.getOrDefault(date, null);
            if (null == posAnalyses) {
                posAnalysisMap.put(date, initPosAnalyses(retailerCode, date));
            } else {
                if (posAnalyses.size() < TOTAL_ANALYSIS_NUM) {
                    final List<String> tagsOfExists = posAnalyses.stream().map(EtlOriPosAnalysis::getTag).collect(Collectors.toList());
                    final List<String> lessTags = TAGS_OF_ORI.stream().filter(item -> !tagsOfExists.contains(item)).collect(Collectors.toList());
                    lessTags.forEach(tag -> posAnalyses.add(new EtlOriPosAnalysis(retailerCode, date, tag)));
                    posAnalysisMap.put(date, posAnalyses);
                }
            }
        });
        return posAnalysisMap;
    }

    private List<EtlOriPosAnalysis> initPosAnalyses(String retailerCode, java.sql.Date realDataDate) {
        List<EtlOriPosAnalysis> posAnalyses = new ArrayList<>();
        posAnalyses.add(new EtlOriPosAnalysis(retailerCode, realDataDate, "1"));
        posAnalyses.add(new EtlOriPosAnalysis(retailerCode, realDataDate, "0"));
        posAnalyses.add(new EtlOriPosAnalysis(retailerCode, realDataDate, "2"));
        return posAnalyses;
    }
}
