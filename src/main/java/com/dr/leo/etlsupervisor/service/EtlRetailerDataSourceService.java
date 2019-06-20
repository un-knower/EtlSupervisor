package com.dr.leo.etlsupervisor.service;

import com.dr.leo.etlsupervisor.entity.EtlRetailerDataSource;

/**
 * @author longping.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019/6/19 15:07
 * @since 1.0
 */
public interface EtlRetailerDataSourceService {
    /**
     * 查询数据源账户配置
     *
     * @param bannerCode 零售商code
     * @param sourceType 数据源类型
     * @return 某一个账号配置
     */
    EtlRetailerDataSource findOneByRetailerAndSourceType(String bannerCode, String sourceType);
}
