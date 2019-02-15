package com.dr.leo.etlsupervisor.azkaban;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

import java.io.File;


/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/15 11:50
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Component
public class AzkabanSparkCommand extends AbstractAzkabanCommand {
    private static final String SPARK_TEMPLATE_HAS_DEPENDENCIES_PATH = "plugin/azkaban_spark_job_template_with_dependencies";
    private static final String SPARK_TEMPLATE_NO_DEPENDENCIES_PATH = "plugin/azkaban_spark_job_template_no_dependencies";
    private int driverMemory;
    private int executorMemory;
    private int executorNums;
    private int memoryOverhead;
    private int networkTimeout;
    private String className;
    private String jarName;
    private String jars;
    private String args;


    @Override
    public void outTemplate(IAzkabanCommand azkabanCommand) {
        AzkabanSparkCommand azkabanSparkCommand = (AzkabanSparkCommand) azkabanCommand;
        File saveJobFile = getAzkabanJobSaveFile(azkabanSparkCommand.getProjectName(), azkabanSparkCommand.getJobName());
        if (StrUtil.isBlank(azkabanSparkCommand.getDependenciesJobName())) {
            outTemplate(saveJobFile, azkabanCommand, SPARK_TEMPLATE_NO_DEPENDENCIES_PATH);
        } else {
            outTemplate(saveJobFile, azkabanCommand, SPARK_TEMPLATE_HAS_DEPENDENCIES_PATH);
        }
    }
}
