package com.dr.leo.etlsupervisor.service;

import com.dr.leo.etlsupervisor.entity.EtlRetailerConfig;

import java.util.List;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/14 15:27
 */
public interface EtlRetailerConfigService {
    /**
     * 查询所有零售商配置
     *
     * @return 零售商所有配置列表
     */
    List<EtlRetailerConfig> showAllRetailerConfig();
}
