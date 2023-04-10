package org.Camp.Model.Mapper;

import org.Camp.Model.Entities.Camp;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CampMapper implements RowMapper<Camp> {

    @Override
    public Camp mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Camp camp = new Camp();
        camp.setId(resultSet.getLong("id"));
        camp.setName(resultSet.getString("name"));
        camp.setLocation(resultSet.getString("location"));
        camp.setPrice(resultSet.getBigDecimal("price"));
        camp.setStock(resultSet.getInt("stock"));
        return camp;
    }
}
