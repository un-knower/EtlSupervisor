package com.dr.leo.etlsupervisor.controller;

import com.dr.leo.etlsupervisor.common.RestResponseResult;
import com.dr.leo.etlsupervisor.entity.EtlDataDownloadTask;
import com.dr.leo.etlsupervisor.exception.ServiceException;
import com.dr.leo.etlsupervisor.job.param.DownloadJobParam;
import com.dr.leo.etlsupervisor.service.impl.EtlDataDownloadTaskServiceImpl;
import com.dr.leo.etlsupervisor.service.impl.FtpFileServiceImpl;
import com.dr.leo.etlsupervisor.task.RetailerDataDownloadTask;
import com.dr.leo.etlsupervisor.thread.EtlDownloadTaskThreadPool;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 零售商数据获取
 *
 * @author longping.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019/6/19 15:19
 * @since 1.0
 */
@RestController
@RequestMapping("/retailer/data")
public class RetailerDataGetController extends BaseRestController {
    private final FtpFileServiceImpl ftpFileService;
    private final EtlDataDownloadTaskServiceImpl downloadTaskService;

    public RetailerDataGetController(FtpFileServiceImpl ftpFileService, EtlDataDownloadTaskServiceImpl downloadTaskService) {
        this.ftpFileService = ftpFileService;
        this.downloadTaskService = downloadTaskService;
    }

    @GetMapping("/ftp")
    public RestResponseResult downloadFtpData(DownloadJobParam downloadJobParam) {
        RetailerDataDownloadTask downloadTask = new RetailerDataDownloadTask(ftpFileService, downloadJobParam);
        final String taskId = downloadJobParam.getRetailerCode() + "-" + downloadJobParam.getDataDate();
        EtlDownloadTaskThreadPool.getInstance().addDownloadTaskJob(taskId, downloadTask);
        return success();
    }

    @GetMapping("/list/tasks")
    public RestResponseResult listDownloadTask() {
        Map<String, List<EtlDataDownloadTask>> data = new HashMap<>(1);
        data.put("taskItems", downloadTaskService.list());
        return success(data);
    }

    @GetMapping("/delete/task/{taskId}")
    public RestResponseResult deleteDownloadTask(@PathVariable Integer taskId) {
        EtlDataDownloadTask task = downloadTaskService.findById(taskId);
        if (null == task) {
            throw new ServiceException(taskId + " 不存在！");
        }
        if ("RUNNING".equals(task.getMark())) {
            throw new ServiceException(taskId + " 正在运行！");
        }
        downloadTaskService.delete(taskId);
        return success();
    }

    @GetMapping("/stop/task/{taskId}")
    public RestResponseResult stopDownloadTask(@PathVariable Integer taskId) {
        EtlDataDownloadTask task = downloadTaskService.findById(taskId);
        if (null == task) {
            throw new ServiceException(taskId + " 不存在！");
        }
        final String taskExecutorId = task.getRetailerCode() + "-" + task.getDataDate();
        boolean cancelRes = EtlDownloadTaskThreadPool.getInstance().cancelOneTask(taskExecutorId);
        if (cancelRes) {
            downloadTaskService.updateMarkOfOneTask(taskId, "KILLED");
        }
        return success();
    }
}
