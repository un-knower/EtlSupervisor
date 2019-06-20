package com.dr.leo.etlsupervisor.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 零售商数据类型
 *
 * @author longping.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019/6/19 14:57
 * @since 1.0
 */
@Entity
@Data
public class EtlRetailerDataSource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String dataSourceType;
    private String bannerCode;
    private String host;
    private Integer port;
    private String username;
    private String password;
    private String encoding;
    private boolean overwrite;
    private String remotePath;
    private String localRootPath;
    private String hdfsRootPath;
}
