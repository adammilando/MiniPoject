package org.Camp.Model.Mapper;

import org.Camp.Model.Request.ReservationRequest;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationRequestMapper implements RowMapper<ReservationRequest> {

    @Override
    public ReservationRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.setId(rs.getLong("id"));
        reservationRequest.setUserName(rs.getString("user_name"));
        reservationRequest.setCampName(rs.getString("camp_name"));
        reservationRequest.setStartDate(rs.getDate("start_date").toLocalDate());
        reservationRequest.setEndDate(rs.getDate("end_date").toLocalDate());
        reservationRequest.setCheckedOut(rs.getBoolean("checked_out"));
        reservationRequest.setNumberOfSpots(rs.getInt("number_of_spots"));
        return reservationRequest;
    }
}
