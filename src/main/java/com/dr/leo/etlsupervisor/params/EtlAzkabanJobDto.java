package com.dr.leo.etlsupervisor.params;

import com.dr.leo.etlsupervisor.entity.EtlAzkabanJob;
import com.dr.leo.etlsupervisor.params.valid.First;
import com.dr.leo.etlsupervisor.params.valid.Fourth;
import com.dr.leo.etlsupervisor.params.valid.Second;
import com.dr.leo.etlsupervisor.params.valid.Third;
import com.google.common.base.Converter;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/4/29 15:45
 */
@Data
@GroupSequence(value = {First.class, Second.class, Third.class, Fourth.class, EtlAzkabanJobDto.class})
public class EtlAzkabanJobDto {
    private Integer id;
    @NotBlank(message = "项目名称不能为空！", groups = {First.class})
    private String project;
    @NotBlank(message = "工作流名称不能为空！", groups = {Second.class})
    private String flow;
    @NotBlank(message = "项目所属用户不能为空！", groups = {Third.class})
    private String user;
    private String email;
    @NotBlank(message = "项目描述不能为空！", groups = {Fourth.class})
    private String description;
    private Timestamp create;
    private Timestamp lastUpdateTime;

    public EtlAzkabanJob convertTo() {
        AzkabanJobConvert azkabanJobConvert = new AzkabanJobConvert();
        return azkabanJobConvert.doForward(this);
    }

    private static class AzkabanJobConvert extends Converter<EtlAzkabanJobDto, EtlAzkabanJob> {
        @Override
        protected EtlAzkabanJob doForward(EtlAzkabanJobDto etlAzkabanJobDto) {
            EtlAzkabanJob azkabanJob = new EtlAzkabanJob();
            BeanUtils.copyProperties(etlAzkabanJobDto, azkabanJob);
            return azkabanJob;
        }

        @Override
        protected EtlAzkabanJobDto doBackward(EtlAzkabanJob azkabanJob) {
            throw new AssertionError("不可逆向转换！");
        }
    }
}
