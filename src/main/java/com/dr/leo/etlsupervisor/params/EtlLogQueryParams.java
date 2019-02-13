package com.dr.leo.etlsupervisor.params;

import lombok.Data;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/13 17:37
 */
@Data
public class EtlLogQueryParams {
    private String pickDate;
    private String keyWords;
    private Integer pageNo;
    private Integer pageSize;
}
