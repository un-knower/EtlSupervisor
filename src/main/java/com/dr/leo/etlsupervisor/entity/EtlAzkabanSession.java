package com.dr.leo.etlsupervisor.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/18 15:37
 */
@Data
@Entity
public class EtlAzkabanSession implements Serializable {
    @Id
    private String sessionId;
    private LocalDateTime expiredDate;
}
