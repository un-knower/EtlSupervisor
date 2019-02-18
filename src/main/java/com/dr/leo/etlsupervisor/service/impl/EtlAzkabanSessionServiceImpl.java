package com.dr.leo.etlsupervisor.service.impl;

import com.dr.leo.etlsupervisor.entity.EtlAzkabanSession;
import com.dr.leo.etlsupervisor.repository.EtlAzkabanSessionRepository;
import com.dr.leo.etlsupervisor.service.EtlAzkabanSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/18 15:44
 */
@Service
public class EtlAzkabanSessionServiceImpl implements EtlAzkabanSessionService {

    private final EtlAzkabanSessionRepository azkabanSessionRepository;

    @Autowired
    public EtlAzkabanSessionServiceImpl(EtlAzkabanSessionRepository azkabanSessionRepository) {
        this.azkabanSessionRepository = azkabanSessionRepository;
    }

    @Override
    public EtlAzkabanSession queryAzkabanSessionId() {
        List<EtlAzkabanSession> etlAzkabanSessions = azkabanSessionRepository.findAll();
        if (etlAzkabanSessions.isEmpty()) {
            return null;
        }
        return etlAzkabanSessions.get(0);
    }

    @Override
    public void saveAzkabanSessionId(EtlAzkabanSession etlAzkabanSession) {
        azkabanSessionRepository.save(etlAzkabanSession);
    }

    @Override
    public void deleteSessionById(String sessionId) {
        azkabanSessionRepository.deleteById(sessionId);
    }
}
