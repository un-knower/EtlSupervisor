package com.dr.leo.etlsupervisor.controller;

import com.dr.leo.etlsupervisor.azkaban.AzkabanSparkCommand;
import com.dr.leo.etlsupervisor.common.RestResponseResult;
import com.dr.leo.etlsupervisor.config.ServerConfig;
import com.dr.leo.etlsupervisor.params.AzkabanSparkCommandParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/14 16:47
 */
@RestController
@RequestMapping("/api/v1/templates")
public class TemplatesApiController {
    private final AzkabanSparkCommand sparkCommand;
    private final ServerConfig serverConfig;

    @Autowired
    public TemplatesApiController(AzkabanSparkCommand sparkCommand, ServerConfig serverConfig) {
        this.sparkCommand = sparkCommand;
        this.serverConfig = serverConfig;
    }

    @PostMapping("/sparkCommand/add")
    public RestResponseResult addSparkJobTemplate(@Validated AzkabanSparkCommandParams sparkCommandParams,
                                                  MultipartHttpServletRequest jarFile) {
        MultipartFile putCommandFile = jarFile.getFile("jarFile");
        if (null != putCommandFile) {
            String putFileName = putCommandFile.getOriginalFilename();
            System.out.println(putFileName);
        }
        String host = serverConfig.getUrl();
        AzkabanSparkCommand azkabanSparkCommand = sparkCommandParams.convertTo();
        String templateUrl = sparkCommand.outTemplate(azkabanSparkCommand);
        String url = host + "/" + templateUrl.split("/static/")[1] + ".job";
        return RestResponseResult.ok(url);
    }
}
