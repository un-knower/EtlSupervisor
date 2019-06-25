package com.dr.leo.etlsupervisor.job.param;

import lombok.Data;

/**
 * @author longping.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019/6/25 11:14
 * @since 1.0
 */
@Data
public class DownloadJobParam {
    private String retailerCode;
    private String dataDate;
    private String dataType;
    private boolean overwrite;
    private boolean clearLocalFile;
}
