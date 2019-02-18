package com.dr.leo.etlsupervisor.service;

import com.dr.leo.etlsupervisor.entity.EtlAzkabanSession;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/18 15:42
 */
public interface EtlAzkabanSessionService {
    /**
     * 查询阿兹卡班session id
     *
     * @return azkaban session
     */
    EtlAzkabanSession queryAzkabanSessionId();

    /**
     * 保存session id
     *
     * @param etlAzkabanSession 需要保存的session对象
     */
    void saveAzkabanSessionId(EtlAzkabanSession etlAzkabanSession);

    /**
     * 根据session id 删除session
     *
     * @param sessionId session id
     */
    void deleteSessionById(String sessionId);
}
