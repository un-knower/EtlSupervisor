package com.dr.leo.etlsupervisor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/13 15:10
 */
@Controller
public class IndexPageController {
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

}
