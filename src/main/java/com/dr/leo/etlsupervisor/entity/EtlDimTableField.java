package com.dr.leo.etlsupervisor.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 维表数据信息
 *
 * @author longping.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019/6/26 14:12
 * @since 1.0
 */
@Data
@Entity
public class EtlDimTableField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String dbName;
    private String tableName;
    private String field;
    private String description;
    private Long createdOn;
    private String createdBy;
    private Long updateOn;
    private String updateBy;
    /**
     * 是否禁用
     */
    private int disable;
}
