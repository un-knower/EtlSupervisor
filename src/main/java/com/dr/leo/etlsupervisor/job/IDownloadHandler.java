package com.dr.leo.etlsupervisor.job;

import com.dr.leo.etlsupervisor.common.RestResponseResult;
import com.dr.leo.etlsupervisor.job.param.DownloadJobParam;

/**
 * 数据下载的接口
 *
 * @author longping.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019/6/25 11:12
 * @since 1.0
 */
public interface IDownloadHandler {
    /**
     * 开始下载任务
     *
     * @param downloadJobParam 下载参数
     * @return 任务执行状态
     */
    RestResponseResult<String> startDownload(DownloadJobParam downloadJobParam);
}
