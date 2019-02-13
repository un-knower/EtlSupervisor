package com.dr.leo.etlsupervisor.common;

import java.time.Instant;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/13 14:19
 */
public class DateUtil {
    public static long nowTimeStamp() {
        return Instant.now().getEpochSecond();
    }
}
