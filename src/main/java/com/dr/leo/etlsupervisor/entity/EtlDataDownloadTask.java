package com.dr.leo.etlsupervisor.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author longping.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019/6/21 10:45
 * @since 1.0
 */
@Data
@Entity
public class EtlDataDownloadTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String retailerCode;
    private String dataDate;
    private String remotePath;
    private String localPath;
    private long submitTime;
    private long endTime;
    private String dataType;
    private String mark;
}
