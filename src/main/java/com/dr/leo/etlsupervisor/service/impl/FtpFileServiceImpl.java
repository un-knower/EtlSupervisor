package com.dr.leo.etlsupervisor.service.impl;

import cn.hutool.core.util.StrUtil;
import com.dr.leo.etlsupervisor.common.EtlSupervisorConst;
import com.dr.leo.etlsupervisor.entity.EtlDataDownloadTask;
import com.dr.leo.etlsupervisor.entity.EtlRetailerDataSource;
import com.dr.leo.etlsupervisor.exception.ServiceException;
import com.dr.leo.etlsupervisor.ftp.ConnectConfig;
import com.dr.leo.etlsupervisor.ftp.FtpHelper;
import com.dr.leo.etlsupervisor.service.FtpFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * ftp数据下载到临时目录里
 *
 * @author longping.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019/6/19 14:48
 * @since 1.0
 */
@Slf4j
@Service
public class FtpFileServiceImpl implements FtpFileService {
    private final HdfsServiceImpl hdfsService;
    private final EtlRetailerDataSourceServiceImpl dataSourceService;
    private final EtlDataDownloadTaskServiceImpl downloadTaskService;

    public FtpFileServiceImpl(EtlRetailerDataSourceServiceImpl dataSourceService, HdfsServiceImpl hdfsService, EtlDataDownloadTaskServiceImpl downloadTaskService) {
        this.dataSourceService = dataSourceService;
        this.hdfsService = hdfsService;
        this.downloadTaskService = downloadTaskService;
    }

    public void downloadMyjData(String day, boolean overwrite) throws IOException {
        EtlRetailerDataSource dataSource = dataSourceService.findFtpDataSource(EtlSupervisorConst.MYJ_CODE);
        final String remotePath = getMyjDownloadRemotePath(dataSource, day);
        log.info("当前下载数据的远程地址:" + remotePath);
        final String localPath = getMyjDownloadLocalSavePath(dataSource, day);
        log.info("当前下载数据保存的本地路径:" + localPath);
        final String hdfsPath = getRetailerDataHdfsPath(dataSource, day);
        log.info("当前下载数据上传的hdfs路径:" + hdfsPath);

        downloadRetailerData(dataSource, remotePath, localPath, day);
        hdfsService.upload(localPath, hdfsPath, overwrite);
    }


    @Override
    public void download(ConnectConfig connectConfig, String remotePath, String localPath) throws IOException {
        FtpHelper.download(connectConfig, remotePath, localPath);
    }

    private void download(EtlRetailerDataSource dataSource, String remotePath, String localPath, String dataDay) throws IOException {
        ConnectConfig connectConfig = new ConnectConfig();
        connectConfig.setHost(dataSource.getHost())
                .setPort(dataSource.getPort())
                .setUsername(dataSource.getUsername())
                .setPassword(dataSource.getPassword())
                .setEncoding(dataSource.getEncoding())
                .setRemotePath(dataSource.getRemotePath());
        FtpHelper.download(connectConfig, remotePath, localPath);
    }


    private void downloadRetailerData(EtlRetailerDataSource dataSource, String remotePath, String localSavePath, String dataDay) throws IOException {
        EtlDataDownloadTask downloadTask = new EtlDataDownloadTask();
        downloadTask.setRetailerCode(dataSource.getBannerCode());
        downloadTask.setDataDate(dataDay);
        downloadTask.setRemotePath(remotePath);
        downloadTask.setLocalPath(localSavePath);
        downloadTask.setSubmitTime(System.currentTimeMillis());
        downloadTask.setEndTime(0L);
        downloadTask.setMark("RUNNING");
        downloadTaskService.save(downloadTask);
        download(dataSource, remotePath, localSavePath, dataDay);

    }


    /**
     * 获取美宜佳的本地下载地址
     *
     * @param dataSource 数据源元数据配置
     * @param day        日期
     * @return 本地下载文件
     */
    private String getMyjDownloadLocalSavePath(EtlRetailerDataSource dataSource, String day) {
        return getRetailerDataDownloadLocalSavePath(dataSource, EtlSupervisorConst.MYJ_CODE,
                day.replaceAll("-", ""));
    }

    /**
     * 获取美宜佳远程的下载路径，分天
     *
     * @param dataSource 数据源路径
     * @param day        日期
     * @return 远程文件路径
     */
    private String getMyjDownloadRemotePath(EtlRetailerDataSource dataSource, String day) {
        return dataSource.getRemotePath().concat(day.replaceAll("-", ""));
    }

    private String getRetailerDataHdfsPath(EtlRetailerDataSource dataSource, String day) {
        String hdfsRootPath = dataSource.getHdfsRootPath();
        if (StrUtil.isBlank(hdfsRootPath)) {
            throw new ServiceException("hdfs路径不能为空！");
        }
        return hdfsRootPath.concat(day.replaceAll("-", ""));
    }

    private String getRetailerDataDownloadLocalSavePath(EtlRetailerDataSource dataSource, String bannerCode, String day) {
        String localRootPath = dataSource.getLocalRootPath();
        if (StrUtil.isBlank(localRootPath)) {
            localRootPath = System.getProperty("java.io.tmpdir");
        }
        localRootPath = localRootPath.concat(File.separator).concat(bannerCode).concat(File.separator).concat(day).concat(File.separator);
        final File localFile = new File(localRootPath);
        if (!localFile.exists()) {
            localFile.mkdirs();
        }
        return localFile.getPath();
    }

}
