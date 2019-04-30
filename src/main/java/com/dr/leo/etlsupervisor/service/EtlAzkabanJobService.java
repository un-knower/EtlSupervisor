package com.dr.leo.etlsupervisor.service;

import com.dr.leo.etlsupervisor.entity.EtlAzkabanJob;

import java.util.List;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/4/29 15:32
 */
public interface EtlAzkabanJobService {
    /**
     * 新增一个job
     *
     * @param azkabanJob azkaban job obj
     * @return 主键
     */
    Integer addAzkabanJob(EtlAzkabanJob azkabanJob);

    /**
     * 修改一个azkaban job
     *
     * @param azkabanJob azkaban job obj
     */
    void updateAzkabanJob(EtlAzkabanJob azkabanJob);

    /**
     * 删除一个job
     *
     * @param id 主键id
     */
    void deleteAzkabanJob(Integer id);

    /**
     * 查询所有azkaban job
     *
     * @return azkaban job列表
     */
    List<EtlAzkabanJob> allJobs();

    /**
     * 查询一个
     *
     * @param project 项目名
     * @return job obj
     */
    EtlAzkabanJob findOneJob(String project);

    /**
     * 查询一个
     *
     * @param project 项目名
     * @param flow    流名称
     * @return job obj
     */
    EtlAzkabanJob findOneJob(String project, String flow);
}
