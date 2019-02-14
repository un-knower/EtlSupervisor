package com.dr.leo.etlsupervisor.params;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/13 17:37
 */
@Data
public class EtlLogQueryParams {
    private String pickDate;
    private String keyWords;
    private Integer pageNo;
    private Integer pageSize;

    public LocalDateTime getPickDateTimeStart() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(this.pickDate + " 00:00:00", formatter);
    }

    public LocalDateTime getPickDateTimeEnd() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(this.pickDate + " 23:59:59", formatter);
    }
}
