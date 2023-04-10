package org.Camp.Model.Mapper;

import org.Camp.Model.Entities.Rating;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RatingMapper implements RowMapper<Rating> {

    @Override
    public Rating mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Rating rating = new Rating();
        rating.setId(resultSet.getLong("id"));
        rating.setUserId(resultSet.getLong("user_id"));
        rating.setCampId(resultSet.getLong("camp_id"));
        rating.setScore(resultSet.getInt("score"));
        rating.setComment(resultSet.getString("comment"));
        return rating;
    }
}
