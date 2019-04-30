package com.dr.leo.etlsupervisor.service.impl;

import com.dr.leo.etlsupervisor.dao.EtlEntityManager;
import com.dr.leo.etlsupervisor.entity.EtlAzkabanJob;
import com.dr.leo.etlsupervisor.repository.EtlAzkabanJobRepository;
import com.dr.leo.etlsupervisor.service.EtlAzkabanJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/4/29 15:38
 */
@Service
public class EtlAzkabanJobServiceImpl implements EtlAzkabanJobService {
    private final EtlAzkabanJobRepository azkabanJobRepository;

    private final EtlEntityManager<EtlAzkabanJob> entityManager;

    @Autowired
    public EtlAzkabanJobServiceImpl(EtlAzkabanJobRepository azkabanJobRepository, EtlEntityManager<EtlAzkabanJob> entityManager) {
        this.azkabanJobRepository = azkabanJobRepository;
        this.entityManager = entityManager;
    }

    @Override
    public Integer addAzkabanJob(EtlAzkabanJob azkabanJob) {
        final String sql = "insert into etl_azkaban_job (project,flow,user,email,description) " +
                "values (:project,:flow,:user,:email,:description)";
        return entityManager.save(sql, azkabanJob);
    }

    @Override
    public void updateAzkabanJob(EtlAzkabanJob azkabanJob) {
        final String sql = "update etl_azkaban_job set project=:project,flow=:flow," +
                "user=:user,email=:email,description=:description where id=:id";
        entityManager.update(sql, azkabanJob);

    }

    @Override
    public void deleteAzkabanJob(Integer id) {
        azkabanJobRepository.deleteById(id);
    }

    @Override
    public List<EtlAzkabanJob> allJobs() {
        return azkabanJobRepository.queryAllByIdAfterOrderByLastUpdateTimeDesc(0);
    }

    @Override
    public EtlAzkabanJob findOneJob(String project) {
        return azkabanJobRepository.findByProject(project);
    }

    @Override
    public EtlAzkabanJob findOneJob(String project, String flow) {
        return azkabanJobRepository.findByProjectAndFlow(project, flow);
    }
}
