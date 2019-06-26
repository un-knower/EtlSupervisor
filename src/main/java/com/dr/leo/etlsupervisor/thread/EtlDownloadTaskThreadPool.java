package com.dr.leo.etlsupervisor.thread;

import com.dr.leo.etlsupervisor.common.RestResponseResult;
import com.dr.leo.etlsupervisor.exception.ServiceException;
import com.dr.leo.etlsupervisor.task.ICallable;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author longping.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019/6/25 10:39
 * @since 1.0
 */
@Slf4j
public class EtlDownloadTaskThreadPool {
    private final ThreadPoolExecutor taskPool = new ThreadPoolExecutor(8, 200, 60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(500), r ->
            new Thread(r, "LeoEtlSupervisor-EtlDownloadTaskThreadPool-" + r.hashCode()));
    private final ConcurrentHashMap<String, FutureTask<RestResponseResult>>
            taskMap = new ConcurrentHashMap<>(8);
    private final ConcurrentHashMap<String, ICallable<RestResponseResult>>
            taskSubmitMap = new ConcurrentHashMap<>(8);


    private static class TaskThreadPoolHolder {
        private static EtlDownloadTaskThreadPool instance = new EtlDownloadTaskThreadPool();
    }

    public static EtlDownloadTaskThreadPool getInstance() {
        return TaskThreadPoolHolder.instance;
    }

    public void addDownloadTaskJob(String taskId, ICallable<RestResponseResult> task) {
        if (taskMap.containsKey(taskId)) {
            throw new ServiceException(taskId + " 任务应已经提交了！");
        }
        FutureTask<RestResponseResult> futureTask = new FutureTask<>(task);
        taskPool.submit(futureTask);
        taskMap.put(taskId, futureTask);
        taskSubmitMap.put(taskId, task);
        EtlDownloadTaskCallbackThread.getInstance().start(taskId);
    }

    public FutureTask<RestResponseResult> getTask(String taskId) {
        return taskMap.getOrDefault(taskId, null);
    }

    public boolean clearOneTask(String taskId) {
        FutureTask task = getTask(taskId);
        if (task == null) {
            return true;
        } else {
            taskMap.remove(taskId);
            return true;
        }
    }

    public boolean cancelOneTask(String taskId) {

        FutureTask task = getTask(taskId);
        if (task == null) {
            return true;
        } else {
            if (!task.isCancelled() && !Thread.currentThread().isInterrupted()) {
                task.cancel(true);
            }
            return clearOneTask(taskId);
        }
    }

    public void stop() {
        taskPool.shutdownNow();
        log.info(">>>>>>>>> etl-supervisor  thread pool shutdown success.");
    }
}
