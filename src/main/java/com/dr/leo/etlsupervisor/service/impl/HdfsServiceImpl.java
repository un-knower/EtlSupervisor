package com.dr.leo.etlsupervisor.service.impl;

import com.dr.leo.etlsupervisor.plugin.HdfsFilePlugin;
import com.dr.leo.etlsupervisor.service.HdfsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author longping.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019/6/19 14:40
 * @since 1.0
 */
@Slf4j
@Service
public class HdfsServiceImpl implements HdfsService {
    @Override
    public void upload(String localFilePath, String hdfsFilePath, boolean overwrite) throws IOException {
        HdfsFilePlugin hdfsFilePlugin = new HdfsFilePlugin();
        hdfsFilePlugin.uploadDir(localFilePath, hdfsFilePath, overwrite);
        hdfsFilePlugin.closeDfs();
    }
}
