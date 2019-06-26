package com.dr.leo.etlsupervisor.repository;

import com.dr.leo.etlsupervisor.entity.EtlDimTableField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author longping.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019/6/26 14:15
 * @since 1.0
 */
@Repository
public interface EtlDimTableFieldRepository extends JpaRepository<EtlDimTableField, Integer>,
        JpaSpecificationExecutor<EtlDimTableField> {
    /**
     * 更新维表信息
     *
     * @param id          id
     * @param dbName      数据库名
     * @param tableName   数据表名
     * @param field       字段
     * @param description 描述
     */
    @Query(value = "update EtlDimTableField set dbName=:dbName,tableName=:tableName," +
            "field=:field,description=:description where id=:id")
    @Modifying
    @Transactional(rollbackFor = {Exception.class})
    void updateField(@Param("id") int id,
                     @Param("dbName") String dbName,
                     @Param("tableName") String tableName,
                     @Param("field") String field,
                     @Param("description") String description);


    /**
     * 是否禁用字段
     *
     * @param id      id
     * @param disable 0,1 0 不禁用，1 禁用
     */
    @Query(value = "update EtlDimTableField set disable=:disable where id=:id")
    @Modifying
    @Transactional(rollbackFor = {Exception.class})
    void disableFieldOrNot(@Param("id") int id, @Param("disable") int disable);

    /**
     * 根据数据库名和表名查找一个配置
     *
     * @param dbName    数据库名
     * @param tableName 数据表名
     * @return 维表配置
     */
    EtlDimTableField findByDbNameAndTableName(String dbName, String tableName);


}
