package com.dr.leo.etlsupervisor.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/4/29 16:42
 */
@Component
public class EtlEntityManager<T> {
    private final NamedParameterJdbcTemplate parameterJdbcTemplate;

    @Autowired
    public EtlEntityManager(NamedParameterJdbcTemplate parameterJdbcTemplate) {
        this.parameterJdbcTemplate = parameterJdbcTemplate;
    }

    public int save(final String sql, T t) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource ps = new BeanPropertySqlParameterSource(t);
        parameterJdbcTemplate.update(sql, ps, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    public void update(final String sql, T t) {
        SqlParameterSource ps = new BeanPropertySqlParameterSource(t);
        parameterJdbcTemplate.update(sql, ps);
    }
}
