package com.dr.leo.etlsupervisor.service.impl;

import com.dr.leo.etlsupervisor.entity.EtlRetailerConfig;
import com.dr.leo.etlsupervisor.repository.EtlRetailerConfigRepository;
import com.dr.leo.etlsupervisor.service.EtlRetailerConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/14 15:28
 */
@Service
public class EtlRetailerConfigServiceImpl implements EtlRetailerConfigService {

    private final EtlRetailerConfigRepository retailerConfigRepository;

    @Autowired
    public EtlRetailerConfigServiceImpl(EtlRetailerConfigRepository retailerConfigRepository) {
        this.retailerConfigRepository = retailerConfigRepository;
    }

    @Override
    public List<EtlRetailerConfig> showAllRetailerConfig() {
        return retailerConfigRepository.findAll();
    }
}
