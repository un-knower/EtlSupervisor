package com.dr.leo.etlsupervisor.ftp;

/**
 * FTP连接配置文件
 *
 * @author longping.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019/6/18 14:02
 * @since 1.0
 */
public class ConnectConfig {
    private String host;
    private int port = 21;
    private String encoding = "ISO-8859-1";
    private String username;
    private String password;
    private String remotePath;

    public ConnectConfig() {
    }

    public ConnectConfig(String host, int port, String encoding, String username, String password) {
        this.host = host;
        this.port = port;
        this.encoding = encoding;
        this.username = username;
        this.password = password;
    }

    public ConnectConfig(String host, String encoding, String username, String password) {
        this.host = host;
        this.encoding = encoding;
        this.username = username;
        this.password = password;
    }

    public ConnectConfig(String host, String username, String password) {
        this.host = host;
        this.username = username;
        this.password = password;
    }

    public ConnectConfig(String encoding) {
        this.encoding = encoding;
    }

    public String getHost() {
        return host;
    }

    public ConnectConfig setHost(String host) {
        this.host = host;
        return this;
    }

    public int getPort() {
        return port;
    }

    public ConnectConfig setPort(int port) {
        this.port = port;
        return this;
    }

    public String getEncoding() {
        return encoding;
    }

    public ConnectConfig setEncoding(String encoding) {
        this.encoding = encoding;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public ConnectConfig setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public ConnectConfig setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getRemotePath() {
        return remotePath;
    }

    public ConnectConfig setRemotePath(String remotePath) {
        this.remotePath = remotePath;
        return this;
    }

}
