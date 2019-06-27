package com.dr.leo.etlsupervisor.service.impl;

import com.dr.leo.etlsupervisor.entity.EtlFileCheckRecord;
import com.dr.leo.etlsupervisor.repository.EtlFileCheckRecordRepository;
import com.dr.leo.etlsupervisor.service.EtlFileCheckRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/4/28 11:34
 */
@Service
public class EtlFileCheckRecordServiceImpl implements EtlFileCheckRecordService {
    private final EtlFileCheckRecordRepository etlFileCheckRecordRepository;

    @Autowired
    public EtlFileCheckRecordServiceImpl(EtlFileCheckRecordRepository etlFileCheckRecordRepository) {
        this.etlFileCheckRecordRepository = etlFileCheckRecordRepository;
    }

    @Override
    public List<EtlFileCheckRecord> checkRecordsByDayAndBanner(String retailerCode, String day) {
        return etlFileCheckRecordRepository.findAllByBannerAndDay(retailerCode, day);
    }

    @Override
    public List<EtlFileCheckRecord> checkRecordsByBanner(String retailerCode) {
        return etlFileCheckRecordRepository.findAllByBanner(retailerCode);
    }

    @Override
    public void saveCheckFileRecord(EtlFileCheckRecord fileCheckRecord) {
        etlFileCheckRecordRepository.save(fileCheckRecord);
    }

    @Override
    public void deleteOne(String retailerCode, String fileName, String fileType) {
        etlFileCheckRecordRepository.deleteByBannerAndFileNameAndFileType(retailerCode, fileName, fileType);
    }
}
