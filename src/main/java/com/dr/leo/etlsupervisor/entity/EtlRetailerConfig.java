package com.dr.leo.etlsupervisor.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/14 15:23
 */
@Entity
@Data
public class EtlRetailerConfig implements Serializable {
    @Id
    private String retailerCode;
    private String retailerEnName;
    private String retailerName;
    private Integer realDay;
    private Integer promotionFilterType;
}
