package com.dr.leo.etlsupervisor.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * 系统日志
 * ClassName: EtlSystemLogs <br/>
 * date: 18-11-21 下午10:15 <br/>
 *
 * @author leo <br/>
 * @version jquery3.2.1.0 <br/>
 */
@Data
@Entity
public class EtlSystemLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String retailerCode;
    private String msg;
    private String logLevel;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime logTime;
    private String logType;
    private String worker;
}

