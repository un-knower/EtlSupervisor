package com.dr.leo.etlsupervisor.controller;

import cn.hutool.core.util.StrUtil;
import com.dr.leo.etlsupervisor.common.RestResponseResult;
import com.dr.leo.etlsupervisor.entity.EtlFileCheckRecord;
import com.dr.leo.etlsupervisor.service.impl.EtlFileCheckRecordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/4/28 11:40
 */
@RestController
@RequestMapping("/api/v1/file/record")
public class EtlFileRecordCheckController {
    private final static String CHECK_TYPE = "check";
    private final static String REAL_TYPE = "real";
    private final EtlFileCheckRecordServiceImpl etlFileCheckRecordService;

    @Autowired
    public EtlFileRecordCheckController(EtlFileCheckRecordServiceImpl etlFileCheckRecordService) {
        this.etlFileCheckRecordService = etlFileCheckRecordService;
    }

    @GetMapping("/one")
    public RestResponseResult fileCheckRecord(@RequestParam(defaultValue = "R10001") String retailerCode,
                                              @RequestParam(defaultValue = "2019-04-27") String day) {
        List<EtlFileCheckRecord> records = etlFileCheckRecordService.checkRecordsByDayAndBanner(retailerCode, day);
        if (records.isEmpty()) {
            RestResponseResult res = RestResponseResult.ok();
            res.setCode(5000);
            return res;
        }
        final Map<String, List<EtlFileCheckRecord>> fileRecordsMap
                = records.stream().collect(Collectors.groupingBy(EtlFileCheckRecord::getFileName));
        List<String> files = new ArrayList<>(2);
        List<Integer> realFileRowNums = new ArrayList<>(2);
        List<Integer> checkFileRowNums = new ArrayList<>(2);
        List<Integer> differenceNums = new ArrayList<>(2);

        fileRecordsMap.forEach((fileName, recordList) -> {
            files.add(StrUtil.sub(fileName, 0, fileName.length() - 15));
            final Integer[] realRowNum = {0};
            final Integer[] checkRowNum = {0};
            recordList.forEach(etlFileCheckRecord -> {
                if (REAL_TYPE.equalsIgnoreCase(etlFileCheckRecord.getFileType())) {
                    realRowNum[0] = etlFileCheckRecord.getRowNumber();
                }
                if (CHECK_TYPE.equalsIgnoreCase(etlFileCheckRecord.getFileType())) {
                    checkRowNum[0] = etlFileCheckRecord.getRowNumber();
                }
            });
            realFileRowNums.add(realRowNum[0]);
            checkFileRowNums.add(checkRowNum[0]);
            differenceNums.add(realRowNum[0] - checkRowNum[0]);
        });
        Map<String, Object> data = new HashMap<>(2);
        data.put("files", files);
        data.put("realRowNumList", realFileRowNums);
        data.put("checkRowNumList", checkFileRowNums);
        data.put("differenceNumList", differenceNums);
        return RestResponseResult.ok(data);
    }

}
