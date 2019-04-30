package com.dr.leo.etlsupervisor.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.dr.leo.etlsupervisor.azkaban.model.*;
import com.dr.leo.etlsupervisor.common.RestResponseResult;
import com.dr.leo.etlsupervisor.exception.ServiceException;
import com.dr.leo.etlsupervisor.service.impl.AzkabanRestApiServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/18 16:32
 */
@RestController
@RequestMapping("/api/v1/azkaban")
public class AzkabanApiController {
    private final AzkabanRestApiServiceImpl azkabanRestApiService;

    @Autowired
    public AzkabanApiController(AzkabanRestApiServiceImpl azkabanRestApiService) {
        this.azkabanRestApiService = azkabanRestApiService;
    }


    @GetMapping("/fetch/flows")
    public RestResponseResult fetchFlows(String project) {
        try {
            String sessionId = azkabanRestApiService.getAzkabanSessionId();
            AzkabanProject azkabanProject = azkabanRestApiService.fetchFlowsOfOneProject(sessionId, project);
            if (StrUtil.isNotBlank(azkabanProject.getError())) {
                return RestResponseResult.failed().message(azkabanProject.getError());
            }
            Map<String, AzkabanProject> data = MapUtil.newHashMap(1);
            data.put("azkabanProject", azkabanProject);
            return RestResponseResult.ok(data);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return RestResponseResult.ok();
    }


    @GetMapping("/fetch/jobs")
    public RestResponseResult fetchJobsOfOneFlow(String project, String flow) {
        try {
            String sessionId = azkabanRestApiService.getAzkabanSessionId();
            AzkabanFlowGraph azkabanFlowGraph = azkabanRestApiService.fetchJobsOfOneFlow(sessionId, project, flow);
            if (StrUtil.isNotBlank(azkabanFlowGraph.getError())) {
                return RestResponseResult.failed().message(azkabanFlowGraph.getError());
            }
            Map<String, AzkabanFlowGraph> data = MapUtil.newHashMap(1);
            data.put("azkabanFlowGraph", azkabanFlowGraph);
            return RestResponseResult.ok(data);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return RestResponseResult.ok();
    }

    @GetMapping("/execute/flow")
    public RestResponseResult executeFlow(String project, String flow) {
        String sessionId;
        try {
            sessionId = azkabanRestApiService.getAzkabanSessionId();
            AzkabanExecutorResponse azkabanExecutorResponse =
                    azkabanRestApiService.executeFlow(sessionId, project, flow);
            if (StrUtil.isNotBlank(azkabanExecutorResponse.getError())) {
                return RestResponseResult.failed().message(azkabanExecutorResponse.getError());
            }
            Map<String, AzkabanExecutorResponse> data = MapUtil.newHashMap(1);
            data.put("executorResponse", azkabanExecutorResponse);
            return RestResponseResult.ok(data);

        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return RestResponseResult.failed().message("系统未知错误！");
    }

    @GetMapping("/fetch/executor/{execid}")
    public RestResponseResult fetchJobOfExecutor(@PathVariable Integer execid) {
        try {
            String sessionId = azkabanRestApiService.getAzkabanSessionId();
            AzkabanExecutorFinishResponse res = azkabanRestApiService.fetchOneFlowOfExecutor(sessionId, execid);
            if (StrUtil.isNotBlank(res.getError())) {
                return RestResponseResult.failed().message(res.getError());
            }
            Map<String, AzkabanExecutorFinishResponse> data = MapUtil.newHashMap(1);
            data.put("executorFinishResponse", res);
            return RestResponseResult.ok(data);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return RestResponseResult.ok();
    }

    @GetMapping("/fetch/executions")
    public RestResponseResult fetchExecutionsOfOneFlow(String project, String flow, Integer start, Integer length) {
        try {
            String sessionId = azkabanRestApiService.getAzkabanSessionId();
            AzkabanExecutionsOfOneFlow azkabanExecutionsOfOneFlow =
                    azkabanRestApiService.fetchExecutionsOfOneFlow(sessionId, project, flow, start, length);
            if (StrUtil.isNotBlank(azkabanExecutionsOfOneFlow.getError())) {
                return RestResponseResult.failed().message(azkabanExecutionsOfOneFlow.getError());
            }
            Map<String, AzkabanExecutionsOfOneFlow> data = MapUtil.newHashMap(1);
            data.put("executionsOfOneFlow", azkabanExecutionsOfOneFlow);
            return RestResponseResult.ok(data);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return RestResponseResult.ok();
    }
}
