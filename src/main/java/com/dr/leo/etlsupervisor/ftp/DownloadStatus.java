package com.dr.leo.etlsupervisor.ftp;

/**
 * 下载状态
 *
 * @author longping.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019/6/18 14:26
 * @since 1.0
 */
public enum DownloadStatus {
    /**
     * 下载文件成功
     */
    DownloadNewSuccess,
    /**
     * 下载文件失败
     */
    DownloadNewFailed,
    /**
     * 本地文件大于远程文件
     */
    LocalFileBiggerThanRemoteFile,
    /**
     * 断点续传成功
     */
    DownloadFromBreakSuccess,
    /**
     * 远程文件不存在
     */
    RemoteFileNotExist,
    /**
     * 断点续传失败
     */
    DownloadFromBreakFailed
}
