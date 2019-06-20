package com.dr.leo.etlsupervisor.controller;

import com.dr.leo.etlsupervisor.common.EtlSupervisorConst;
import com.dr.leo.etlsupervisor.common.RestResponseResult;
import com.dr.leo.etlsupervisor.service.impl.FtpFileServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 零售商数据获取
 *
 * @author longping.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019/6/19 15:19
 * @since 1.0
 */
@RestController
@RequestMapping("/retailer/data")
public class RetailerDataGetController extends BaseRestController {
    private final FtpFileServiceImpl ftpFileService;

    public RetailerDataGetController(FtpFileServiceImpl ftpFileService) {
        this.ftpFileService = ftpFileService;
    }

    @GetMapping("/ftp")
    public RestResponseResult downloadFtpData(@RequestParam String retailerCode,
                                              @RequestParam String day, @RequestParam boolean overwrite) {
        try {
            if (EtlSupervisorConst.MYJ_CODE.equals(retailerCode)) {
                ftpFileService.downloadMyjData(day, overwrite);
            } else {
                return failed("暂时不支持此零售商" + retailerCode + "的数据下载");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return failed("数据下载时出现异常！");
        }
        return success();
    }
}
