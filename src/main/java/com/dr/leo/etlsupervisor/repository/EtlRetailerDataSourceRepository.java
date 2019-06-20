package com.dr.leo.etlsupervisor.repository;

import com.dr.leo.etlsupervisor.entity.EtlRetailerDataSource;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author longping.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019/6/19 15:08
 * @since 1.0
 */
public interface EtlRetailerDataSourceRepository extends JpaRepository<EtlRetailerDataSource, Integer> {

    /**
     * 查询数据源账户配置
     *
     * @param bannerCode     零售商code
     * @param dataSourceType 数据源类型
     * @return 某一个账号配置
     */
    EtlRetailerDataSource getByBannerCodeAndDataSourceType(String bannerCode, String dataSourceType);
}
