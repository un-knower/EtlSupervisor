package com.dr.leo.etlsupervisor.common;

import cn.hutool.core.date.DateUtil;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/13 14:19
 */
public class DateKit {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static long nowTimeStamp() {
        return Instant.now().getEpochSecond();
    }

    public static String yesterday() {
        return DateUtil.format(DateUtil.yesterday(), "yyyy-MM-dd");
    }

    public static LocalDateTime toLocalDateTime(String datetime) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        return LocalDateTime.parse(datetime, df);
    }

    public static String toDateTimeStr(LocalDateTime dateTime) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        return df.format(dateTime);
    }
}
