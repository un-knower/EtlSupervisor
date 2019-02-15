package com.dr.leo.etlsupervisor.common;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/15 21:40
 */
public enum LogLevelEnum {
    /**
     * 日志级别
     */
    DEBUG("debug"),
    INFO("info"),
    WARN("warning"),
    DANGER("danger");
    private String logLevel;

    LogLevelEnum(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getLogLevel() {
        return logLevel;
    }}


