package com.dr.leo.etlsupervisor.controller;

import com.dr.leo.etlsupervisor.azkaban.AzkabanSparkCommand;
import com.dr.leo.etlsupervisor.common.RestResponseResult;
import com.dr.leo.etlsupervisor.params.AzkabanSparkCommandParams;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/14 16:47
 */
@RestController
@RequestMapping("/api/v1/templates")
public class TemplatesApiController {
    @Resource
    private AzkabanSparkCommand sparkCommand;

    @PostMapping("/sparkCommand/add")
    public RestResponseResult addSparkJobTemplate(@Validated AzkabanSparkCommandParams sparkCommandParams,
                                                  MultipartHttpServletRequest jarFile) {
        MultipartFile putCommandFile = jarFile.getFile("jarFile");
        if (null != putCommandFile) {
            String putFileName = putCommandFile.getOriginalFilename();
            System.out.println(putFileName);
        }
        AzkabanSparkCommand azkabanSparkCommand = sparkCommandParams.convertTo();
        sparkCommand.outTemplate(azkabanSparkCommand);

        return RestResponseResult.ok();
    }
}
