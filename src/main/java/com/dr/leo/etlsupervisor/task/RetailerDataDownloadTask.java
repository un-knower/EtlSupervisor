package com.dr.leo.etlsupervisor.task;

import com.dr.leo.etlsupervisor.common.EtlSupervisorConst;
import com.dr.leo.etlsupervisor.common.RestResponseResult;
import com.dr.leo.etlsupervisor.job.param.DownloadJobParam;
import com.dr.leo.etlsupervisor.service.impl.FtpFileServiceImpl;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * @author longping.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019/6/25 13:57
 * @since 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class RetailerDataDownloadTask implements Callable<RestResponseResult> {
    private FtpFileServiceImpl ftpFileService;
    private DownloadJobParam downloadJobParam;

    @Override
    public RestResponseResult call() throws Exception {
        log.info("子线程：" + Thread.currentThread().getName() + " 正在下载零售商数据！");
        if (EtlSupervisorConst.MYJ_CODE.equals(downloadJobParam.getRetailerCode())) {
            ftpFileService.downloadMyjData(downloadJobParam.getDataDate(), downloadJobParam.isOverwrite());
        }
        return RestResponseResult.ok().message("success");
    }
}
