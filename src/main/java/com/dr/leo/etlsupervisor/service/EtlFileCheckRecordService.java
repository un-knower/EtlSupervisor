package com.dr.leo.etlsupervisor.service;

import com.dr.leo.etlsupervisor.entity.EtlFileCheckRecord;

import java.util.List;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/4/28 11:30
 */
public interface EtlFileCheckRecordService {
    /**
     * 查询文件校验记录by零售商标号以及日期
     *
     * @param retailerCode 零售商编号
     * @param day          日期
     * @return 记录列表
     */
    List<EtlFileCheckRecord> checkRecordsByDayAndBanner(String retailerCode, String day);

    /**
     * 查询文件校验记录by零售商标号
     *
     * @param retailerCode 零售商编号
     * @return 记录列表
     */
    List<EtlFileCheckRecord> checkRecordsByBanner(String retailerCode);
}
