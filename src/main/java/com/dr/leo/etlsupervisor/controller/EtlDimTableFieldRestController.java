package com.dr.leo.etlsupervisor.controller;

import com.dr.leo.etlsupervisor.common.RestResponseResult;
import com.dr.leo.etlsupervisor.entity.EtlDimTableField;
import com.dr.leo.etlsupervisor.exception.ServiceException;
import com.dr.leo.etlsupervisor.params.EtlDimTableFieldDto;
import com.dr.leo.etlsupervisor.service.impl.EtlDimTableFieldServiceImpl;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author longping.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019/6/26 14:43
 * @since 1.0
 */
@RestController
@RequestMapping("/api/v1/dimTable/field")
public class EtlDimTableFieldRestController extends BaseRestController {
    private final EtlDimTableFieldServiceImpl etlDimTableFieldService;

    public EtlDimTableFieldRestController(EtlDimTableFieldServiceImpl etlDimTableFieldService) {
        this.etlDimTableFieldService = etlDimTableFieldService;
    }

    @GetMapping("/list")
    public RestResponseResult allDimTables() {
        Map<String, List<EtlDimTableField>> data = new HashMap<>(1);
        data.put("dimTableList", etlDimTableFieldService.listAll());
        return success(data);
    }

    @PostMapping("/add")
    public RestResponseResult addDimTable(@RequestBody @Validated EtlDimTableFieldDto etlDimTableFieldDto) {
        EtlDimTableField etlDimTableField = etlDimTableFieldService.findByDbNameAndTableName(etlDimTableFieldDto.getDbName(),
                etlDimTableFieldDto.getTableName());
        if (etlDimTableField != null) {
            throw new ServiceException(etlDimTableFieldDto.getDbName() + "." +
                    etlDimTableFieldDto.getTableName() + "已经存在!");
        }
        EtlDimTableField dimTableField = etlDimTableFieldDto.convertTo();
        Map<String, EtlDimTableField> data = new HashMap<>(1);
        data.put("dimTable", etlDimTableFieldService.save(dimTableField));
        return success(data);
    }

    @PostMapping("/update")
    public RestResponseResult updateDimTable(@RequestBody @Validated EtlDimTableFieldDto etlDimTableFieldDto) {
        EtlDimTableField etlDimTableField = etlDimTableFieldService.findByDbNameAndTableName(etlDimTableFieldDto.getDbName(),
                etlDimTableFieldDto.getTableName());
        if (etlDimTableField != null && etlDimTableFieldDto.getId().intValue() != etlDimTableField.getId().intValue()) {
            throw new ServiceException(etlDimTableFieldDto.getDbName() + "." +
                    etlDimTableFieldDto.getTableName() + "已经存在!");
        }
        EtlDimTableField dimTableField = etlDimTableFieldDto.convertTo();
        etlDimTableFieldService.update(dimTableField);
        return success();
    }

    @GetMapping("/delete/{id}")
    public RestResponseResult deleteDimTable(@PathVariable Integer id) {
        etlDimTableFieldService.delete(id);
        return success();
    }


    @GetMapping("/disable/{id}")
    public RestResponseResult disableDimTable(@PathVariable Integer id) {
        etlDimTableFieldService.disableOrNot(id, 1);
        return success();
    }


    @GetMapping("/enable/{id}")
    public RestResponseResult enableDimTable(@PathVariable Integer id) {
        etlDimTableFieldService.disableOrNot(id, 0);
        return success();
    }
}
