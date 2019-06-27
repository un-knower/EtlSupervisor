package com.dr.leo.etlsupervisor.common;

import cn.hutool.core.util.StrUtil;

/**
 * @author longping.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019/6/27 16:41
 * @since 1.0
 */
public class TimeUtils {
    private static String computerRunTime(Long diffTime) {
        Long totalSeconds = diffTime / 1000;
        Long hour = totalSeconds / 3600;
        Long day = 0L;
        if (hour >= 24) {
            day = hour / 24;
            hour = hour % 24;
        }
        Long seconds = totalSeconds % 3600;
        Long min = seconds / 60;
        Long second = seconds % 60;
        if (second > 0 && min == 0 && hour == 0 && day == 0) {
            return StrUtil.format("{}秒", second);
        } else if (second > 0 && min > 0 && hour == 0 && day == 0) {
            return StrUtil.format("{}分钟{}秒", min, second);
        } else if (hour > 0 && day == 0) {
            return StrUtil.format("{}小时{}分钟{}秒", hour, min, second);
        } else if (day > 0) {
            return StrUtil.format("{}天{}小时{}分钟{}秒", day, hour, min, second);
        }

        return "0秒";
    }

    public static void main(String[] args) {
        System.out.println(computerRunTime(1561620574682L - 1561620354304L));
    }
}
