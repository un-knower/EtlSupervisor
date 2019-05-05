package com.dr.leo.etlsupervisor.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.dr.leo.etlsupervisor.common.RestResponseResult;
import com.dr.leo.etlsupervisor.entity.EtlAzkabanJob;
import com.dr.leo.etlsupervisor.params.EtlAzkabanJobDto;
import com.dr.leo.etlsupervisor.service.impl.EtlAzkabanJobServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/4/29 15:52
 */
@RestController
@RequestMapping("/api/v1/azkaban/job")
public class AzkabanJobController {
    private final EtlAzkabanJobServiceImpl azkabanJobService;

    @Autowired
    public AzkabanJobController(EtlAzkabanJobServiceImpl azkabanJobService) {
        this.azkabanJobService = azkabanJobService;
    }

    @PostMapping("/add")
    public RestResponseResult addAzkabanJob(@Validated @RequestBody EtlAzkabanJobDto azkabanJobDto) {
        EtlAzkabanJob etlAzkabanJob = azkabanJobService.findOneJob(azkabanJobDto.getProject(), azkabanJobDto.getFlow());
        if (null != etlAzkabanJob) {
            return RestResponseResult.failed().message(StrUtil.format("{} {} 已经存在！",
                    azkabanJobDto.getProject(), azkabanJobDto.getFlow()));
        }
        etlAzkabanJob = azkabanJobDto.convertTo();
        Integer pkId = azkabanJobService.addAzkabanJob(etlAzkabanJob);
        if (pkId < 1) {
            return RestResponseResult.failed().message(StrUtil.format("{}添加失败！", azkabanJobDto.getProject()));
        }
        Map<String, Integer> data = MapUtil.newHashMap(1);
        data.put("jobPkId", pkId);
        return RestResponseResult.ok(data);
    }

    @PostMapping("/update")
    public RestResponseResult updateAzkabanJob(@Validated @RequestBody EtlAzkabanJobDto azkabanJobDto) {
        EtlAzkabanJob etlAzkabanJob = azkabanJobService.findOneJob(azkabanJobDto.getProject());
        if (null != etlAzkabanJob && etlAzkabanJob.getId().intValue() != azkabanJobDto.getId().intValue()) {
            return RestResponseResult.failed().message(StrUtil.format("{}已经存在！",
                    azkabanJobDto.getProject()));
        }
        etlAzkabanJob = azkabanJobService.findOneJob(azkabanJobDto.getProject(), azkabanJobDto.getFlow());
        if (null != etlAzkabanJob && etlAzkabanJob.getId().intValue() != azkabanJobDto.getId().intValue()) {
            return RestResponseResult.failed().message(StrUtil.format("{} {} 已经存在！",
                    azkabanJobDto.getProject(), azkabanJobDto.getFlow()));
        }
        etlAzkabanJob = azkabanJobDto.convertTo();
        azkabanJobService.updateAzkabanJob(etlAzkabanJob);
        return RestResponseResult.ok();
    }

    @GetMapping("/all")
    public RestResponseResult allAzkabanJobs() {
        List<EtlAzkabanJob> azkabanJobs = azkabanJobService.allJobs();
        Map<String, List<EtlAzkabanJob>> data = MapUtil.newHashMap(1);
        data.put("azkabanJobs", azkabanJobs);
        return RestResponseResult.ok(data);
    }

    @GetMapping("/delete/{azkabanJobId}")
    public RestResponseResult deleteOneJob(@PathVariable Integer azkabanJobId) {
        azkabanJobService.deleteAzkabanJob(azkabanJobId);
        return RestResponseResult.ok();
    }
}
