package com.dr.leo.etlsupervisor.ftp;

import java.io.IOException;
import java.util.List;

/**
 * @author longping.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019/6/18 15:09
 * @since 1.0
 */
public class FtpHelper {

    public static DownloadStatus download(ConnectConfig connectConfig, String remoteFolderPath, String localFolderPath) throws IOException {
        FtpCore ftp = getFtpCore(connectConfig, remoteFolderPath);
        return ftp.download(remoteFolderPath, localFolderPath);
    }

    public static List<String> listFiles(ConnectConfig connectConfig, String remoteFolderPath) throws IOException {
        FtpCore ftp = getFtpCore(connectConfig, remoteFolderPath);
        return ftp.getFileList(remoteFolderPath);
    }

    private static FtpCore getFtpCore(ConnectConfig connectConfig, String remoteFolderPath) throws IOException {
        FtpCore ftp = new FtpCore();
        boolean connectRes = ftp.connectFtpServer(connectConfig);
        if (!connectRes) {
            throw new FtpException("server " + connectConfig.getHost() + " connect failed!");
        }
        if (ftp.isExistsRemoteDir(remoteFolderPath)) {
            throw new FtpException("remote dir " + remoteFolderPath + " is not exists!");
        }
        return ftp;
    }
}
