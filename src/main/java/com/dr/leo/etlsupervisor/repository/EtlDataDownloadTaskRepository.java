package com.dr.leo.etlsupervisor.repository;

import com.dr.leo.etlsupervisor.entity.EtlDataDownloadTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author longping.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019/6/21 10:50
 * @since 1.0
 */
@Repository
public interface EtlDataDownloadTaskRepository extends JpaRepository<EtlDataDownloadTask, Integer> {
    /**
     * 更改
     *
     * @param taskId  任务id
     * @param mark    标记状态
     * @param endTime 结束时间
     */
    @Query(value = "update EtlDataDownloadTask set mark=:mark,endTime=:endTime where id=:taskId")
    @Transactional(rollbackFor = {Exception.class})
    @Modifying
    void updateTaskMark(@Param(value = "taskId") int taskId,
                        @Param(value = "mark") String mark,
                        @Param(value = "endTime") long endTime);
}
