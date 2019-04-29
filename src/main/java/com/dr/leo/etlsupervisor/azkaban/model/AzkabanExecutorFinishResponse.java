package com.dr.leo.etlsupervisor.azkaban.model;

import lombok.Data;

/**
 * azkaban任务执行结果查看
 *
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/4/29 14:32
 */
@Data
public class AzkabanExecutorFinishResponse {
    private String project;
    private Long updateTime;
    private String type;
    private Integer attempt;
    private Integer execid;
    private Long submitTime;
    private Long startTime;
    private Long endTime;
    private String flowId;
    private String flow;
    private String status;
    private String error;
}
