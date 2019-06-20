package com.dr.leo.etlsupervisor.common;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/15 13:47
 */
public interface EtlSupervisorConst {
    String CLASS_PATH = EtlSupervisorUtil.getCurrentClassPath();
    long AZKABAN_SESSION_EXPIRED = 8L;
    String AZKABAN_RES_OK = "success";
    String AZKABAN_STATUS = "status";
    String AZKABAN_PROJECT_ID = "projectId";
    String AZKABAN_PROJECT_EXISTS = "Project already exists.";
    int AZKABAN_SC_OK = 200;
    String AZKABAN_PARENT_PATH = "azkaban";

    String ORI_POS_TYPE = "ori_pos";

    String FTP = "ftp";
    String MYJ_CODE = "R10001";
}
