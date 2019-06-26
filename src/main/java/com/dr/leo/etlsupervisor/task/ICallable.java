package com.dr.leo.etlsupervisor.task;

import java.util.concurrent.Callable;

/**
 * @author longping.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019/6/25 17:02
 * @since 1.0
 */
public interface ICallable<T> extends Callable<T> {
    /**
     * 任务中断
     */
    void cancel();
}
