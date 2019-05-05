package com.dr.leo.etlsupervisor.controller;

import cn.hutool.core.util.StrUtil;
import com.dr.leo.etlsupervisor.azkaban.model.AzkabanExecutionsOfOneFlow;
import com.dr.leo.etlsupervisor.common.Pageable;
import com.dr.leo.etlsupervisor.common.RestResponseResult;
import com.dr.leo.etlsupervisor.exception.ServiceException;
import com.dr.leo.etlsupervisor.service.impl.AzkabanRestApiServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/13 15:10
 */
@Controller
@Slf4j
public class IndexPageController {
    private final AzkabanRestApiServiceImpl azkabanRestApiService;

    @Autowired
    public IndexPageController(AzkabanRestApiServiceImpl azkabanRestApiService) {
        this.azkabanRestApiService = azkabanRestApiService;
    }

    @GetMapping("/404")
    public String pageNotFound() {
        return "404";
    }

    @GetMapping(value = {"/", "/index"})
    public String index() {
        return "index";
    }

    @GetMapping("/pos_analysis")
    public String posAnalysis() {
        return "pos_analysis";
    }

    @GetMapping("/pos_record_analysis")
    public String posRecordAnalysis() {
        return "pos_record_analysis";
    }

    @GetMapping("/pos_watcher")
    public String posWatcher() {
        return "pos_watcher";
    }

    @GetMapping("/logs")
    public String logs() {
        return "logs";
    }

    @GetMapping("/templates")
    public String templates() {
        return "templates";
    }

    @GetMapping("/settings")
    public String settings() {
        return "settings";
    }

    @GetMapping("/azkaban_job")
    public String azkabanJob() {
        return "azkaban_job";
    }

    @GetMapping("/azkaban_job_execute_history")
    public ModelAndView azkabanJobExecuteHistory(String project, String flow, Integer pageNo, Integer pageSize) {
        log.info("project：" + project + " flow：" + flow);
        String sessionId;
        ModelAndView view;
        try {
            sessionId = azkabanRestApiService.getAzkabanSessionId();
            AzkabanExecutionsOfOneFlow azkabanExecutionsOfOneFlow =
                    azkabanRestApiService.fetchExecutionsOfOneFlow(sessionId, project, flow,
                            (pageNo - 1) * pageSize, pageSize);
            if (StrUtil.isNotBlank(azkabanExecutionsOfOneFlow.getError())) {
                return new ModelAndView("no_data");
            }
            if (azkabanExecutionsOfOneFlow.getExecutions().isEmpty()) {
                return new ModelAndView("no_data");
            }
            view = new ModelAndView("azkaban_job_execute_history");
            Pageable pageable = new Pageable(pageSize, azkabanExecutionsOfOneFlow.getTotal());
            view.addObject("executionsHistory", azkabanExecutionsOfOneFlow);
            int[] sliders = pageable.getSlider(pageNo == 0 ? pageNo + 1 : pageNo);
            view.addObject("pageSliders", sliders);
            view.addObject("totalPage", "总页数: " + pageable.getPages());
            return view;

        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return new ModelAndView("no_data");
    }

}
