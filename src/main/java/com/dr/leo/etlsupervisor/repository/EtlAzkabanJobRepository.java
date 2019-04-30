package com.dr.leo.etlsupervisor.repository;

import com.dr.leo.etlsupervisor.entity.EtlAzkabanJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/4/29 15:31
 */
@Repository
public interface EtlAzkabanJobRepository extends JpaRepository<EtlAzkabanJob, Integer>,
        JpaSpecificationExecutor<EtlAzkabanJob> {
    /**
     * 查询所有
     *
     * @param id 主键id
     * @return 所有job列表
     */
    List<EtlAzkabanJob> queryAllByIdAfterOrderByLastUpdateTimeDesc(Integer id);

    /**
     * 查找一个project
     *
     * @param project 项目名称
     * @return 结果
     */
    EtlAzkabanJob findByProject(String project);

    /**
     * 查找一个project
     *
     * @param project 项目名称
     * @param flow    流名称
     * @return 结果
     */
    EtlAzkabanJob findByProjectAndFlow(String project, String flow);
}
