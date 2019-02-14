package com.dr.leo.etlsupervisor.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Date;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2018/11/23 16:33
 */
@Data
@Entity
public class EtlOriPosAnalysis implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String retailerCode;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date realDataDay;
    private String tag;
    private Integer totalFlowCount = 0;
    private Integer totalFlowNoCount = 0;
    private Float totalQuantity = 0.0f;
    private Float totalAmount = 0.0f;

    public EtlOriPosAnalysis() {
    }

    public EtlOriPosAnalysis(String retailerCode, Date realDataDay, String tag) {
        this.retailerCode = retailerCode;
        this.realDataDay = realDataDay;
        this.tag = tag;
    }
}
