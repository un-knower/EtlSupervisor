package com.dr.leo.etlsupervisor.task;

import com.dr.leo.etlsupervisor.common.EtlSupervisorConst;
import com.dr.leo.etlsupervisor.common.RestResponseResult;
import com.dr.leo.etlsupervisor.job.param.DownloadJobParam;
import com.dr.leo.etlsupervisor.service.impl.FtpFileServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * @author longping.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019/6/25 13:57
 * @since 1.0
 */
@Slf4j
public class RetailerDataDownloadTask implements ICallable<RestResponseResult> {
    private volatile boolean isStop = false;
    private FtpFileServiceImpl ftpFileService;
    private DownloadJobParam downloadJobParam;

    public RetailerDataDownloadTask(FtpFileServiceImpl ftpFileService, DownloadJobParam downloadJobParam) {
        this.ftpFileService = ftpFileService;
        this.downloadJobParam = downloadJobParam;
    }

    @Override
    public RestResponseResult call() throws Exception {
        log.info("子线程：" + Thread.currentThread().getName() + " 正在下载零售商数据！");
        while (!isStop && !Thread.currentThread().isInterrupted()) {
            if (EtlSupervisorConst.MYJ_CODE.equals(downloadJobParam.getRetailerCode())) {
                ftpFileService.downloadMyjData(downloadJobParam.getDataDate(), downloadJobParam.isOverwrite());
                break;
            }
        }

        return RestResponseResult.ok().message("success");
    }

    @Override
    public void cancel() {
        isStop = true;
    }
}
