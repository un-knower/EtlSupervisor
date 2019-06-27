package com.dr.leo.etlsupervisor.thread;

import com.dr.leo.etlsupervisor.common.RestResponseResult;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author longping.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019/6/25 11:26
 * @since 1.0
 */
@Slf4j
public class EtlDownloadTaskCallbackThread {
    private static EtlDownloadTaskCallbackThread instance = new EtlDownloadTaskCallbackThread();

    public static EtlDownloadTaskCallbackThread getInstance() {
        return instance;
    }

    /**
     * callBack Thread
     */

    private final ThreadPoolExecutor taskCallbackPool = new ThreadPoolExecutor(
            8, 200, 60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(500), r -> {
        Thread t = new Thread(r, "LeoEtlSupervisor-EtlDownloadTaskThreadPool-" + r.hashCode());
        t.setDaemon(true);
        return t;
    });

    public void start(String taskId) {
        taskCallbackPool.execute(() -> {
            FutureTask<RestResponseResult> task = EtlDownloadTaskThreadPool.getInstance().getTask(taskId);
            while (true) {
                if (task != null) {
                    if (task.isDone()) {
                        log.info("task {} 已经执行完成！", taskId);
                        try {
                            RestResponseResult result = task.get();
                            System.out.println(result.getMsg());
                            break;
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                            break;
                        }
                    } else if (task.isCancelled()) {
                        log.info("task {} 已经被取消！", taskId);
                        break;
                    } else {
                        //log.info("task {} 在运行！", taskId);
                        //break;
                    }
                }
            }
        });
    }


}
