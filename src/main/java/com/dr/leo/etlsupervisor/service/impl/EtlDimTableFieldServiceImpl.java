package com.dr.leo.etlsupervisor.service.impl;

import com.dr.leo.etlsupervisor.entity.EtlDimTableField;
import com.dr.leo.etlsupervisor.repository.EtlDimTableFieldRepository;
import com.dr.leo.etlsupervisor.service.EtlDimTableFieldService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author longping.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019/6/26 14:37
 * @since 1.0
 */
@Service
public class EtlDimTableFieldServiceImpl implements EtlDimTableFieldService {
    private final EtlDimTableFieldRepository dimTableFieldRepository;

    public EtlDimTableFieldServiceImpl(EtlDimTableFieldRepository dimTableFieldRepository) {
        this.dimTableFieldRepository = dimTableFieldRepository;
    }

    @Override
    public EtlDimTableField save(EtlDimTableField etlDimTableField) {
        return dimTableFieldRepository.save(etlDimTableField);
    }

    @Override
    public List<EtlDimTableField> listAll() {
        return dimTableFieldRepository.findAll();
    }

    @Override
    public void update(EtlDimTableField etlDimTableField) {
        dimTableFieldRepository.updateField(etlDimTableField.getId(), etlDimTableField.getDbName(),
                etlDimTableField.getTableName(), etlDimTableField.getField(), etlDimTableField.getDescription());
    }

    @Override
    public void delete(int id) {
        dimTableFieldRepository.deleteById(id);
    }

    @Override
    public void disableOrNot(int id, int disable) {
        dimTableFieldRepository.disableFieldOrNot(id, disable);
    }

    @Override
    public EtlDimTableField findByDbNameAndTableName(String dbName, String tableName) {
        return dimTableFieldRepository.findByDbNameAndTableName(dbName, tableName);
    }
}
