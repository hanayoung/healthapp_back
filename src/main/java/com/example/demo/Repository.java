package com.example.demo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    public void saveImg(String fileName,String user, String info) {
        String sql = "INSERT INTO img(name,user,info) VALUES (?,?,?)";
        jdbcTemplate.update(sql,fileName,user,info);
    }

    public List<ImgVo> getImgData(String userName){
        String sql = "SELECT name, user, info FROM img WHERE user = ?";
        return jdbcTemplate.query(sql, new PreparedStatementSetter(){
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, userName); // Assuming 'userName' is the variable containing the user name value
            }
        },imgVoRowMapper);
    }

    public RowMapper<ImgVo> imgVoRowMapper = (resultSet, rowNum) -> {
        ImgVo imgVo= new ImgVo();
        imgVo.setFileName(resultSet.getString("name"));
        System.out.println("name "+imgVo.getFileName());
        imgVo.setUser(resultSet.getString("user"));
        System.out.println("user "+imgVo.getUser());
        imgVo.setInfo(resultSet.getString("info"));
        System.out.println("info "+imgVo.getInfo());
        return imgVo;
    };
}
