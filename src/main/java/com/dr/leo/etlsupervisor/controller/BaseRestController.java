package com.dr.leo.etlsupervisor.controller;

import com.dr.leo.etlsupervisor.common.RestResponseResult;

/**
 * @author longping.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019/6/20 10:30
 * @since 1.0
 */
public abstract class BaseRestController {
    RestResponseResult success() {
        return RestResponseResult.ok();
    }

    RestResponseResult success(Object data) {
        return RestResponseResult.ok(data);
    }

    RestResponseResult failed() {
        return RestResponseResult.failed();
    }

    RestResponseResult failed(String message) {
        return RestResponseResult.failed().message(message);
    }

}
