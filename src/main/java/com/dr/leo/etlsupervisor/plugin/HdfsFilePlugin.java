package com.dr.leo.etlsupervisor.plugin;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * hdfs 文件插件
 *
 * @author longping.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019/6/18 17:56
 * @since 1.0
 */
@Slf4j
public class HdfsFilePlugin {
    private static final String HDFS = "hdfs";
    private static final String HDFS_SEPARATOR = "/";
    private FileSystem fs;

    public HdfsFilePlugin() {
        this("root");
    }

    public HdfsFilePlugin(String user) {
        System.setProperty("HADOOP_USER_NAME", user);
        Configuration config = new Configuration();
        try {
            fs = FileSystem.get(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void upload(String localFilePath, String dfsFilePath) throws IOException {
        log.info("start upload " + localFilePath + " to " + dfsFilePath);
        FileInputStream in = new FileInputStream(localFilePath);
        OutputStream out = fs.create(new Path(dfsFilePath), () -> System.out.print("."));
        IOUtils.copyBytes(in, out, 4096, true);
    }

    public void uploadDir(String localDirPath, String dfsDirPath) throws IOException {
        mkdir(dfsDirPath);
        FileStatus status = fs.getFileStatus(new Path(dfsDirPath));
        File localDirFile = new File(localDirPath);
        if (status.isFile()) {
            throw new DfsException("hdfs path is not a directory!");
        } else {
            dfsDirPath = cutDir(dfsDirPath);
        }
        File[] localDirFiles = localDirFile.listFiles();
        if (null == localDirFiles) {
            return;
        }
        for (File lFile : localDirFiles) {
            if (lFile.isDirectory()) {
                uploadDir(lFile.getPath(), dfsDirPath);
            } else {
                upload(lFile.getPath(), dfsDirPath + lFile.getName());
            }
        }
    }

    public void mkdir(String dfsFilePath) throws IOException {
        Path path = new Path(dfsFilePath);
        if (!fs.exists(path)) {
            fs.mkdirs(path);
        }
    }

    public void delete(String dfsFilePath) throws IOException {
        Path path = new Path(dfsFilePath);
        fs.deleteOnExit(path);
        log.info(dfsFilePath + " 删除成功！");
    }

    public void closeDfs() throws IOException {
        if (null != fs) {
            fs.close();
        }
    }

    private String cutDir(String str) {
        String[] strS = str.split(HDFS_SEPARATOR);
        StringBuilder result = new StringBuilder();
        if (HDFS.equals(strS[0])) {
            result.append("hdfs://");
            for (int i = 1; i < strS.length; i++) {
                result.append(strS[i]).append(HDFS_SEPARATOR);
            }
        } else {
            for (String s : strS) {
                result.append(s).append(HDFS_SEPARATOR);
            }
        }

        return result.toString();
    }


   /* public static void main(String[] args) {

        HdfsFilePlugin hdfsFilePlugin = new HdfsFilePlugin();
        try {
            hdfsFilePlugin.delete("/etl/pos_data/myj/now/20190615/");
            hdfsFilePlugin.uploadDir("C:\\Users\\leo\\Desktop\\20190615",
                    "/etl/pos_data/myj/now/20190615", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

}
