package com.dr.leo.etlsupervisor.service;

import java.io.IOException;

/**
 * @author longping.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019/6/19 14:36
 * @since 1.0
 */
public interface HdfsService {
    /**
     * hdfs 文件上传
     *
     * @param localFilePath 本地文件路径
     * @param hdfsFilePath  hdfs集群路径
     * @param overwrite     是否覆盖旧有目录
     * @throws IOException 抛出异常
     */
    void upload(String localFilePath, String hdfsFilePath, boolean overwrite) throws IOException;
}
