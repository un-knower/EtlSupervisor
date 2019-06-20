package com.dr.leo.etlsupervisor.service.impl;

import com.dr.leo.etlsupervisor.common.EtlSupervisorConst;
import com.dr.leo.etlsupervisor.entity.EtlRetailerDataSource;
import com.dr.leo.etlsupervisor.repository.EtlRetailerDataSourceRepository;
import com.dr.leo.etlsupervisor.service.EtlRetailerDataSourceService;
import org.springframework.stereotype.Service;

/**
 * @author longping.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019/6/19 15:12
 * @since 1.0
 */
@Service
public class EtlRetailerDataSourceServiceImpl implements EtlRetailerDataSourceService {
    private final EtlRetailerDataSourceRepository etlRetailerDataSourceRepository;

    public EtlRetailerDataSourceServiceImpl(EtlRetailerDataSourceRepository etlRetailerDataSourceRepository) {
        this.etlRetailerDataSourceRepository = etlRetailerDataSourceRepository;
    }

    @Override
    public EtlRetailerDataSource findOneByRetailerAndSourceType(String bannerCode, String sourceType) {
        return etlRetailerDataSourceRepository.getByBannerCodeAndDataSourceType(bannerCode, sourceType);
    }

    public EtlRetailerDataSource findFtpDataSource(String bannerCode) {
        return etlRetailerDataSourceRepository.getByBannerCodeAndDataSourceType(bannerCode, EtlSupervisorConst.FTP);
    }

}
