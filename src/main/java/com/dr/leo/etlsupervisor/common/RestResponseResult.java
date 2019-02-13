package com.dr.leo.etlsupervisor.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/13 14:15
 */
@Data
public class RestResponseResult<T> implements Serializable {
    private boolean success;
    private int code = 200;
    private String msg;
    private long timestamp = DateUtil.nowTimeStamp();
    private T payload;

    public RestResponseResult() {

    }

    public RestResponseResult(boolean success) {
        this.success = success;
    }

    public RestResponseResult(boolean success, T payload) {
        this.success = success;
        this.payload = payload;
    }

    public RestResponseResult<T> success(boolean success) {
        this.success = success;
        return this;
    }

    public RestResponseResult<T> payload(T payload) {
        this.payload = payload;
        return this;
    }

    public RestResponseResult<T> code(int code) {
        this.code = code;
        return this;
    }

    public RestResponseResult<T> message(String msg) {
        this.msg = msg;
        return this;
    }

    public static <T> RestResponseResult<T> ok() {
        return (new RestResponseResult<T>()).success(true);
    }

    public static <T> RestResponseResult<T> ok(T payload) {
        return (new RestResponseResult<T>()).success(true).payload(payload);
    }

    public static <T> RestResponseResult<T> ok(T payload, int code) {
        return (new RestResponseResult<T>()).success(true).payload(payload).code(code);
    }

    public static <T> RestResponseResult<T> failed() {
        return (new RestResponseResult<T>()).success(false);
    }

    public static <T> RestResponseResult<T> failed(T payload) {
        return (new RestResponseResult<T>()).success(false).payload(payload);
    }

    public static <T> RestResponseResult<T> failed(T payload, int code) {
        return (new RestResponseResult<T>()).success(false).payload(payload).code(code);
    }

}
