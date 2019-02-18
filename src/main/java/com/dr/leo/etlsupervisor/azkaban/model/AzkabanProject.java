package com.dr.leo.etlsupervisor.azkaban.model;

import java.util.List;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/18 17:09
 */
public class AzkabanProject {
    private String project;
    private Integer projectId;
    private List<AzkabanFlow> flows;
    private String error;

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public List<AzkabanFlow> getFlows() {
        return flows;
    }

    public void setFlows(List<AzkabanFlow> flows) {
        this.flows = flows;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public static class AzkabanFlow {
        private String flowId;

        public String getFlowId() {
            return flowId;
        }

        public void setFlowId(String flowId) {
            this.flowId = flowId;
        }
    }
}
