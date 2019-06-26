package com.dr.leo.etlsupervisor.params;

import com.dr.leo.etlsupervisor.entity.EtlDimTableField;
import com.dr.leo.etlsupervisor.params.valid.First;
import com.dr.leo.etlsupervisor.params.valid.Fourth;
import com.dr.leo.etlsupervisor.params.valid.Second;
import com.dr.leo.etlsupervisor.params.valid.Third;
import com.google.common.base.Converter;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;

/**
 * @author longping.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019/6/26 16:09
 * @since 1.0
 */
@Data
@GroupSequence(value = {First.class, Second.class, Third.class, Fourth.class, EtlDimTableFieldDto.class})
public class EtlDimTableFieldDto {
    private Integer id;
    @NotBlank(message = "维表输出数据库名称不能为空！", groups = {First.class})
    private String dbName;
    @NotBlank(message = "维表名称不能为空！", groups = {Second.class})
    private String tableName;
    @NotBlank(message = "维表字段不能为空！", groups = {Third.class})
    private String field;
    @NotBlank(message = "维表描述不能为空！", groups = {Fourth.class})
    private String description;

    public EtlDimTableField convertTo() {
        EtlDimTableFieldConvert etlDimTableFieldConvert = new EtlDimTableFieldConvert();
        return etlDimTableFieldConvert.doForward(this);
    }

    private static class EtlDimTableFieldConvert extends Converter<EtlDimTableFieldDto, EtlDimTableField> {
        @Override
        protected EtlDimTableField doForward(EtlDimTableFieldDto etlDimTableFieldDto) {
            EtlDimTableField etlDimTableField = new EtlDimTableField();
            BeanUtils.copyProperties(etlDimTableFieldDto, etlDimTableField);
            return etlDimTableField;
        }

        @Override
        protected EtlDimTableFieldDto doBackward(EtlDimTableField etlDimTableField) {
            throw new AssertionError("不可逆向转换！");
        }
    }
}
