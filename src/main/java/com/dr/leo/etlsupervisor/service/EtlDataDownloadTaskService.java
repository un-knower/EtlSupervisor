package com.dr.leo.etlsupervisor.service;

import com.dr.leo.etlsupervisor.entity.EtlDataDownloadTask;

import java.util.List;

/**
 * @author longping.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019/6/21 10:51
 * @since 1.0
 */
public interface EtlDataDownloadTaskService {
    /**
     * 保存下载任务
     *
     * @param etlDataDownloadTask 下载任务
     * @return 下载任务
     */
    EtlDataDownloadTask save(EtlDataDownloadTask etlDataDownloadTask);

    /**
     * 获取所有下载任务列表
     *
     * @return 下载任务列表
     */
    List<EtlDataDownloadTask> list();

    /**
     * 删除一个下载任务
     *
     * @param taskId 任务id
     */
    void delete(int taskId);

    /**
     * 根据id查找下载任务
     *
     * @param taskId 任务id
     * @return 下载任务
     */
    EtlDataDownloadTask findById(int taskId);

    /**
     * 更改任务状态
     *
     * @param taskId 任务id
     * @param mark   状态标记
     */
    void updateMarkOfOneTask(int taskId, String mark);
}
