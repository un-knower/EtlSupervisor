package com.dr.leo.etlsupervisor.service.impl;

import cn.hutool.core.util.StrUtil;
import com.dr.leo.etlsupervisor.common.EtlSupervisorConst;
import com.dr.leo.etlsupervisor.common.IoUtil;
import com.dr.leo.etlsupervisor.entity.EtlDataDownloadTask;
import com.dr.leo.etlsupervisor.entity.EtlFileCheckRecord;
import com.dr.leo.etlsupervisor.entity.EtlRetailerDataSource;
import com.dr.leo.etlsupervisor.exception.ServiceException;
import com.dr.leo.etlsupervisor.ftp.ConnectConfig;
import com.dr.leo.etlsupervisor.ftp.FtpHelper;
import com.dr.leo.etlsupervisor.service.FtpFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

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
    private final EtlFileCheckRecordServiceImpl checkRecordService;

    public FtpFileServiceImpl(EtlRetailerDataSourceServiceImpl dataSourceService, HdfsServiceImpl hdfsService,
                              EtlDataDownloadTaskServiceImpl downloadTaskService, EtlFileCheckRecordServiceImpl checkRecordService) {
        this.dataSourceService = dataSourceService;
        this.hdfsService = hdfsService;
        this.downloadTaskService = downloadTaskService;
        this.checkRecordService = checkRecordService;
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
        //文件行数校验
        checkFileRecord(EtlSupervisorConst.MYJ_CODE, day, localPath);
        checkMyjFileRecord(EtlSupervisorConst.MYJ_CODE, day, localPath);
        hdfsService.upload(localPath, hdfsPath, overwrite);
        downloadTaskService.updateMarkOfOneTask("SUCCESS", System.currentTimeMillis(),
                EtlSupervisorConst.MYJ_CODE, day);
    }

    private void checkFileRecord(String retailerCode, String day, String localPath) {
        File localFile = new File(localPath);
        if (!localFile.exists()) {
            return;
        }
        String[] fileNames = localFile.list();
        if (fileNames == null || fileNames.length == 0) {
            return;
        }
        for (String fileName : fileNames) {
            final int fileLineNum = IoUtil.getLineNumber(localPath.concat(File.separator).concat(fileName));
            checkRecordService.deleteOne(retailerCode, fileName, "real");
            EtlFileCheckRecord etlFileCheckRecord = new EtlFileCheckRecord();
            etlFileCheckRecord.setBanner(retailerCode);
            etlFileCheckRecord.setDay(day);
            etlFileCheckRecord.setFileType("real");
            etlFileCheckRecord.setFileName(fileName);
            etlFileCheckRecord.setRowNumber(fileLineNum);
            etlFileCheckRecord.setLastUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
            checkRecordService.saveCheckFileRecord(etlFileCheckRecord);
        }

    }

    private void checkMyjFileRecord(String retailerCode, String day, String localPath) {
        final String filePath = localPath.concat(File.separator)
                .concat("bigdata_checklist_" + day.replaceAll("-", "_")).concat(".DAT");
        final File checkFile = new File(filePath);
        if (!checkFile.exists()) {
            return;
        }
        List<String> checkLines = IoUtil.readLines(checkFile);
        checkLines.forEach(line -> {
            String[] content = line.split("\\|");
            if (content.length == 2) {
                final String fileName = content[0].trim().concat("_").concat(day.replaceAll("-", "_")).concat(".DAT");
                checkRecordService.deleteOne(retailerCode, fileName, "check");
                EtlFileCheckRecord etlFileCheckRecord = new EtlFileCheckRecord();
                etlFileCheckRecord.setBanner(retailerCode);
                etlFileCheckRecord.setDay(day);
                etlFileCheckRecord.setFileType("check");
                etlFileCheckRecord.setFileName(fileName);
                etlFileCheckRecord.setRowNumber(Integer.parseInt(content[1]));
                etlFileCheckRecord.setLastUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
                checkRecordService.saveCheckFileRecord(etlFileCheckRecord);
            }
        });
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
