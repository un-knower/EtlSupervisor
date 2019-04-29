package com.dr.leo.etlsupervisor.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * 文件采集记录表
 *
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/4/28 11:27
 */
@Data
@Entity
public class EtlFileCheckRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fileName;
    private Integer rowNumber;
    private String fileType;
    private String day;
    private String banner;
    private Timestamp lastUpdateTime;

}
