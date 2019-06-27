package com.dr.leo.etlsupervisor.service.impl;

import com.dr.leo.etlsupervisor.entity.EtlDataDownloadTask;
import com.dr.leo.etlsupervisor.repository.EtlDataDownloadTaskRepository;
import com.dr.leo.etlsupervisor.service.EtlDataDownloadTaskService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author longping.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019/6/25 10:19
 * @since 1.0
 */
@Service
public class EtlDataDownloadTaskServiceImpl implements EtlDataDownloadTaskService {

    private final EtlDataDownloadTaskRepository downloadTaskRepository;

    public EtlDataDownloadTaskServiceImpl(EtlDataDownloadTaskRepository downloadTaskRepository) {
        this.downloadTaskRepository = downloadTaskRepository;
    }

    @Override
    public EtlDataDownloadTask save(EtlDataDownloadTask etlDataDownloadTask) {
        return downloadTaskRepository.save(etlDataDownloadTask);
    }

    @Override
    public List<EtlDataDownloadTask> list() {
        return downloadTaskRepository.findAll();
    }

    @Override
    public void delete(int taskId) {
        downloadTaskRepository.deleteById(taskId);
    }

    @Override
    public EtlDataDownloadTask findById(int taskId) {
        return downloadTaskRepository.findById(taskId).orElse(null);
    }

    @Override
    public void updateMarkOfOneTask(int taskId, String mark) {
        downloadTaskRepository.updateTaskMark(taskId, mark, System.currentTimeMillis());
    }

    public void updateMarkOfOneTask(String mark, long endTime, String retailerCode, String dataDate) {
        downloadTaskRepository.updateTaskMark(mark, endTime, retailerCode, dataDate);
    }

}
