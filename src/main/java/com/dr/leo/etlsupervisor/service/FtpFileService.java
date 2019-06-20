package com.dr.leo.etlsupervisor.service;

import com.dr.leo.etlsupervisor.ftp.ConnectConfig;

import java.io.IOException;

/**
 * @author longping.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019/6/19 14:45
 * @since 1.0
 */
public interface FtpFileService {
    /**
     * 从ftp上下载数据
     *
     * @param connectConfig 连接配置属性
     * @param remotePath    远程路径
     * @param localPath     本地路径
     * @throws IOException 抛出IO异常
     */
    void download(ConnectConfig connectConfig, String remotePath, String localPath) throws IOException;
}
