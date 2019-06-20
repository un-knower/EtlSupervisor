package com.dr.leo.etlsupervisor.plugin;

/**
 * @author longping.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019/6/18 16:01
 * @since 1.0
 */
public class DfsException extends RuntimeException {
    public DfsException() {
        super();
    }

    public DfsException(String message) {
        super((message));
    }

    public DfsException(String message, Throwable cause) {
        super(message, cause);
    }
}
