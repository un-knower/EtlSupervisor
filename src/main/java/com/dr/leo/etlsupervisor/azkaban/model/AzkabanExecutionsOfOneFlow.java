package com.dr.leo.etlsupervisor.azkaban.model;

import lombok.Data;

import java.util.List;

/**
 * 一个流的执行情况
 *
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/4/29 14:53
 */
@Data
public class AzkabanExecutionsOfOneFlow {
    private Integer total;
    private Integer length;
    private String project;
    private Integer from;
    private Integer projectId;
    private String flow;
    private List<Executions> executions;
    private String error;

    @Data
    public static class Executions {
        private Long submitTime;
        private String submitUser;
        private Long startTime;
        private Long endTime;
        private String flowId;
        private Integer projectId;
        private Integer execId;
        private String status;
    }
}
