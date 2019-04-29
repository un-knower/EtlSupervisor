package com.dr.leo.etlsupervisor.repository;

import com.dr.leo.etlsupervisor.entity.EtlFileCheckRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/4/28 11:35
 */
@Repository
public interface EtlFileCheckRecordRepository extends JpaRepository<EtlFileCheckRecord, Integer> {
    /**
     * 查询全部校验文件列表
     *
     * @param banner 零售商编号
     * @param day    日期
     * @return 校验列表
     */
    List<EtlFileCheckRecord> findAllByBannerAndDay(String banner, String day);

    /**
     * 查询全部校验列表
     *
     * @param banner 零售商编号
     * @return 校验列表
     */
    List<EtlFileCheckRecord> findAllByBanner(String banner);
}
