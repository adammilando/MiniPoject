package org.Camp.Model.Mapper;

import org.Camp.Model.Request.RatingRequest;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RatingRequestMapper implements RowMapper<RatingRequest> {

    @Override
    public RatingRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
        RatingRequest ratingRequest = new RatingRequest();
        ratingRequest.setId(rs.getLong("id"));
        ratingRequest.setUserName(rs.getString("user_name"));
        ratingRequest.setCampName(rs.getString("camp_name"));
        ratingRequest.setScore(rs.getInt("score"));
        ratingRequest.setComment(rs.getString("comment"));
        return ratingRequest;
    }
}
