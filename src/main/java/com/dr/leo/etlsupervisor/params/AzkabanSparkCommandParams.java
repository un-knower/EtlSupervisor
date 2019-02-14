package com.dr.leo.etlsupervisor.params;

import com.dr.leo.etlsupervisor.params.valid.First;
import com.dr.leo.etlsupervisor.params.valid.Fourth;
import com.dr.leo.etlsupervisor.params.valid.Second;
import com.dr.leo.etlsupervisor.params.valid.Third;
import lombok.Data;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/14 16:44
 */
@Data
@GroupSequence(value = {First.class, Second.class, Third.class, Fourth.class, AzkabanSparkCommandParams.class})
public class AzkabanSparkCommandParams {
    private String commandType;
    @NotBlank(message = "项目名称不能为空！", groups = {First.class})
    private String projectName;
    @NotBlank(message = "job/任务名称不能为空！", groups = {Second.class})
    private String jobName;
    private int driverMemory;
    private int executorMemory;
    private int executorNums;
    private int memoryOverhead;
    private int networkTimeout;
    @NotBlank(message = "运作主类名称不能为空！", groups = {Third.class})
    private String className;
    @NotBlank(message = "运行jar包名称不能为空！", groups = {Fourth.class})
    private String jarName;
    private String jars;
    private String args;
}
