package com.dr.leo.etlsupervisor.controller;

import com.dr.leo.etlsupervisor.common.RestResponseResult;
import com.dr.leo.etlsupervisor.service.impl.EtlRetailerConfigServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/14 15:31
 */
@RestController
@RequestMapping("/api/retailer")
public class RetailerConfigApiController {
    private final EtlRetailerConfigServiceImpl retailerConfigService;

    @Autowired
    public RetailerConfigApiController(EtlRetailerConfigServiceImpl retailerConfigService) {
        this.retailerConfigService = retailerConfigService;
    }

    @GetMapping("/list/config")
    public RestResponseResult allRetailerConfig() {
        return RestResponseResult.ok(retailerConfigService.showAllRetailerConfig());
    }
}
