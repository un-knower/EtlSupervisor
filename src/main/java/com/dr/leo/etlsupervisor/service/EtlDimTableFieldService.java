package com.dr.leo.etlsupervisor.service;

import com.dr.leo.etlsupervisor.entity.EtlDimTableField;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author longping.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019/6/26 14:16
 * @since 1.0
 */
public interface EtlDimTableFieldService {
    /**
     * 保存维表信息
     *
     * @param etlDimTableField 维表信息对象
     * @return 保存后的维表对象
     */
    EtlDimTableField save(EtlDimTableField etlDimTableField);

    /**
     * 查找所有
     *
     * @return 所有维表信息列表
     */
    List<EtlDimTableField> listAll();

    /**
     * 更新信息
     *
     * @param etlDimTableField 维表字段信息
     */
    void update(EtlDimTableField etlDimTableField);

    /**
     * 删除一个
     *
     * @param id 删除id
     */
    void delete(int id);

    /**
     * 是否禁用字段
     *
     * @param id      id
     * @param disable 0,1 0 不禁用，1 禁用
     */
    void disableOrNot(int id, int disable);

    /**
     * 根据数据库名和表名查找一个配置
     *
     * @param dbName    数据库名
     * @param tableName 数据表名
     * @return 维表配置
     */
    EtlDimTableField findByDbNameAndTableName(String dbName, String tableName);

    /**
     * 分页查询维表信息
     *
     * @param pageNo   当前页
     * @param pageSize 分页大小
     * @return 分页数据
     */
    Page<EtlDimTableField> findAllDimTables(int pageNo, int pageSize);

}
