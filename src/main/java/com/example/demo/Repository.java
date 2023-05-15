package com.example.demo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

@org.springframework.stereotype.Repository
public class Repository {
    private final JdbcTemplate jdbcTemplate;

    public Repository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveData(HrVo hr) {
        String sql = "INSERT INTO hr(value, time) VALUES (?, ?)";
        jdbcTemplate.update(sql, hr.getValue(), hr.getTime());
    }

    public List<HrVo> getData() {
        String sql = "SELECT value, time FROM hr";
        return jdbcTemplate.query(sql, hrVoRowMapper);
    }

    public RowMapper<HrVo> hrVoRowMapper = (resultSet, rowNum) -> {
        HrVo hrVo = new HrVo();
        hrVo.setValue(resultSet.getInt("value"));
        System.out.println("value "+hrVo.getValue());
        hrVo.setTime(resultSet.getString("time"));
        return hrVo;
    };

    public void saveImg(String fileName) {
        System.out.println("here!");
        String sql = "INSERT INTO img(name) VALUES (?)";
        jdbcTemplate.update(sql,fileName);
    }
}
