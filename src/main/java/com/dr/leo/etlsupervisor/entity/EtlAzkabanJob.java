package com.dr.leo.etlsupervisor.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/4/29 15:28
 */
@Data
@Entity
public class EtlAzkabanJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String project;
    private String flow;
    private String user;
    private String email;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp create;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp lastUpdateTime;
}
