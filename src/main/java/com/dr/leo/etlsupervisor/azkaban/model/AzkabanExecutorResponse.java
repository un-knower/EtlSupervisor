package com.dr.leo.etlsupervisor.azkaban.model;

import lombok.Data;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/4/29 13:50
 */
@Data
public class AzkabanExecutorResponse {
    private String project;
    private String message;
    private String flow;
    private Integer execid;
    private String error;
}
