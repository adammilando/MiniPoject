package org.Camp.Model.Mapper;

import org.Camp.Model.Request.CampRequest;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CampRequestMapper implements RowMapper<CampRequest> {

    @Override
    public CampRequest mapRow(ResultSet resultSet, int rowNum) throws SQLException{
        CampRequest campRequest = new CampRequest();
        campRequest.setName(resultSet.getString("name"));
        campRequest.setLocation(resultSet.getString("location"));
        campRequest.setPrice(resultSet.getBigDecimal("price"));
        campRequest.setStock(resultSet.getInt("stock"));
        return campRequest;
    }
}
