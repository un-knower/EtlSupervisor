package com.dr.leo.etlsupervisor.ftp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author longping.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019/6/18 14:00
 * @since 1.0
 */
@Slf4j
public class FtpCore {
    private FTPClient ftpClient;
    private static final int BUFFER_SIZE = 4096;

    /**
     * 连接ftp服务器
     *
     * @param server     服务器ip
     * @param port       端口，通常为21
     * @param username   用户名
     * @param password   密码
     * @param remotePath 进入服务器之后的默认路径
     * @return 连接成功返回true，否则返回false
     * @throws IOException IO异常
     */
    public boolean connectFtpServer(String server, int port, String encoding, String username,
                                    String password, String remotePath) throws IOException {
        ftpClient = new FTPClient();
        ftpClient.connect(server, port);
        ftpClient.setControlEncoding(encoding);
        log.info("connected to " + server + ":" + port + "!");
        int connectReplyCode = ftpClient.getReplyCode();
        log.info("ftp server reply code: " + connectReplyCode);
        if (FTPReply.isPositiveCompletion(connectReplyCode)) {
            boolean loginRes = ftpClient.login(username, password);
            log.info("ftp server login result: " + loginRes);
            if (loginRes) {
                if (null != remotePath && remotePath.length() != 0) {
                    boolean cdDirRes = ftpClient.changeWorkingDirectory(remotePath);
                    log.info("cd ftp server remote path " + remotePath + " result:" + cdDirRes);
                }
            }
            return true;
        }
        disconnect();
        return false;
    }

    /**
     * 连接FTP服务器
     *
     * @param connectConfig 连接属性
     * @return ftp服务器是否连接成功
     * @throws IOException IO异常
     */
    public boolean connectFtpServer(ConnectConfig connectConfig) throws IOException {
        return connectFtpServer(connectConfig.getHost(), connectConfig.getPort(), connectConfig.getEncoding(),
                connectConfig.getUsername(), connectConfig.getPassword(), connectConfig.getRemotePath());
    }

    /**
     * 断开与远程服务器的连接
     *
     * @throws IOException IO异常
     */
    public void disconnect() throws IOException {
        if (ftpClient.isConnected()) {
            ftpClient.disconnect();
        }
    }

    /**
     * 判断远程目录是否存在
     *
     * @param remoteDirPath 远程文件夹
     * @return 是否存在
     * @throws IOException IO异常
     */
    public boolean isExistsRemoteDir(String remoteDirPath) throws IOException {
        boolean flag = false;
        ftpClient.enterLocalPassiveMode();
        FTPFile[] ftpFiles = ftpClient.listFiles(remoteDirPath);
        for (FTPFile ftpFile : ftpFiles) {
            if (ftpFile.isDirectory() && ftpFile.getName().equals(remoteDirPath)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 获取某一远程文件夹下的所有文件列表
     *
     * @param remoteDirPath 远程文件夹地址
     * @return 文件名列表
     * @throws IOException IO异常
     */
    public List<String> getFileList(String remoteDirPath) throws IOException {
        // 设置被动模式
        ftpClient.enterLocalPassiveMode();
        FTPFile[] ftpFiles = ftpClient.listFiles(remoteDirPath);
        List<String> fileNames = new ArrayList<String>();
        if (null == ftpFiles || ftpFiles.length == 0) {
            return new ArrayList<String>();
        }
        for (FTPFile ftpFile : ftpFiles) {
            if (ftpFile.isFile()) {
                fileNames.add(ftpFile.getName());
            }
        }
        return fileNames;
    }

    /**
     * 下载远程文件夹中的所有文件
     *
     * @param remotePath 远程文件夹路径
     * @param localPath  本地保存文件夹路径
     * @return 下载状态
     * @throws IOException IO异常
     */
    public DownloadStatus download(String remotePath, String localPath) throws IOException {
        // 设置被动模式
        ftpClient.enterLocalPassiveMode();
        // 设置以二进制方式传输
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        DownloadStatus result = null;
        //检查远程文件是否存在
        FTPFile[] ftpFiles = ftpClient.listFiles(new String(remotePath.getBytes("UTF-8"), "iso-8859-1"));
        if (ftpFiles.length < 1) {
            log.error("remote path" + remotePath + "is not exists！");
            return DownloadStatus.RemoteFileNotExist;
        }
        for (FTPFile ftpFile : ftpFiles) {
            final String ftpFileName = ftpFile.getName();
            if (".".equals(ftpFileName)) {
                continue;
            }
            if ("..".equals(ftpFileName)) {
                continue;
            }
            final File localFile = new File(localPath + File.separator + ftpFileName);
            result = downloadCore(remotePath + "/" + ftpFileName, ftpFile, localFile);
        }
        return result;
    }

    private DownloadStatus downloadCore(String remoteFilePath, FTPFile ftpFile, File localFile) throws IOException {
        long remoteFileSize = ftpFile.getSize();
        if (localFile.exists()) {
            long localFileSize = localFile.length();
            if (localFileSize >= remoteFileSize) {
                log.info(remoteFilePath + " download successfully!");
                return DownloadStatus.LocalFileBiggerThanRemoteFile;
            }
            //断点下载
            log.info("start Breakpoint download " + remoteFilePath + " to " + localFile.getPath());
            FileOutputStream out = new FileOutputStream(localFile, true);
            ftpClient.setRestartOffset(localFileSize);
            InputStream in = ftpClient.retrieveFileStream(new String(remoteFilePath.
                    getBytes("UTF-8"), "iso-8859-1"));
            boolean isDone = downloadCore(remoteFilePath, in, out, remoteFileSize, localFileSize);
            if (isDone) {
                return DownloadStatus.DownloadFromBreakSuccess;
            } else {
                return DownloadStatus.DownloadFromBreakFailed;
            }

        } else {
            OutputStream out = new FileOutputStream(localFile);
            InputStream in = ftpClient.retrieveFileStream(new String(remoteFilePath.
                    getBytes("UTF-8"), "iso-8859-1"));
            log.info("start download " + remoteFilePath + " to " + localFile.getPath());
            boolean isDone = downloadCore(remoteFilePath, in, out, remoteFileSize, 0);
            if (isDone) {
                return DownloadStatus.DownloadFromBreakSuccess;
            } else {
                return DownloadStatus.DownloadFromBreakFailed;
            }
        }
    }

    private boolean downloadCore(String ftpFilePath, InputStream in, OutputStream out,
                                 long remoteFileSize, long localFileSize) throws IOException {
        byte[] bytes = new byte[BUFFER_SIZE];
        long step = remoteFileSize / 100;
        // 文件过小，step可能为0
        step = step == 0 ? 1 : step;
        long process = localFileSize / step;
        int c;
        while ((c = in.read(bytes)) != -1) {
            out.write(bytes, 0, c);
            localFileSize += c;
            long nowProcess = localFileSize / step;
            if (nowProcess > process) {
                process = nowProcess;
                if (process % 5 == 0) {
                    log.info("download " + ftpFilePath + " process:" + process + "%");
                }
            }
        }
        in.close();
        out.close();
        return ftpClient.completePendingCommand();
    }
}
