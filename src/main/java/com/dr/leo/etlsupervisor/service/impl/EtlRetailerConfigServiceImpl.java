package com.dr.leo.etlsupervisor.service.impl;

import com.dr.leo.etlsupervisor.entity.EtlRetailerConfig;
import com.dr.leo.etlsupervisor.repository.EtlRetailerConfigRepository;
import com.dr.leo.etlsupervisor.service.EtlRetailerConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/14 15:28
 */
@Service
public class EtlRetailerConfigServiceImpl implements EtlRetailerConfigService {

    @Resource
    private EtlRetailerConfigRepository retailerConfigRepository;

    @Override
    public List<EtlRetailerConfig> showAllRetailerConfig() {
        return retailerConfigRepository.findAll();
    }
}
