package com.dr.leo.etlsupervisor.controller;

import com.dr.leo.etlsupervisor.common.RestResponseResult;
import com.dr.leo.etlsupervisor.params.AzkabanSparkCommandParams;
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
    @PostMapping("/sparkCommand/add")
    public RestResponseResult addSparkJobTemplate(@Validated AzkabanSparkCommandParams sparkCommandParams,
                                                  MultipartHttpServletRequest jarFile/*, BindingResult result*/) {

        MultipartFile putCommandFile = jarFile.getFile("jarFile");
        if (null != putCommandFile) {
            String putFileName = putCommandFile.getOriginalFilename();
            System.out.println(putFileName);
        }
        //TODO 模板文件上传控制器
        //try(FileOutputStream os = new FileOutputStream())
        return RestResponseResult.ok();
    }
}
