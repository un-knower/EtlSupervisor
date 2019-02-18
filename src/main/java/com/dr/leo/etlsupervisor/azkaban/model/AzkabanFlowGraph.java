package com.dr.leo.etlsupervisor.azkaban.model;

import java.util.List;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/18 17:56
 */
public class AzkabanFlowGraph {
    private String project;
    private Integer projectId;
    private String flow;
    private List<AzkabanJob> nodes;
    private String error;

    public static class AzkabanJob {
        private String id;
        private String type;
        private String[] in;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String[] getIn() {
            return in;
        }

        public void setIn(String[] in) {
            this.in = in;
        }
    }

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

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }

    public List<AzkabanJob> getNodes() {
        return nodes;
    }

    public void setNodes(List<AzkabanJob> nodes) {
        this.nodes = nodes;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
