package org.Camp.Model.Mapper;

import org.Camp.Model.Entities.Reservation;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationMapper implements RowMapper<Reservation> {
    @Override
    public Reservation mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setId(resultSet.getLong("id"));
        reservation.setUserId(resultSet.getLong("user_id"));
        reservation.setCampId(resultSet.getLong("camp_id"));
        reservation.setStartDate(resultSet.getDate("start_date").toLocalDate());
        reservation.setEndDate(resultSet.getDate("end_date").toLocalDate());
        reservation.setCheckedOut(resultSet.getBoolean("checked_out"));
        reservation.setNumberOfSpots(resultSet.getInt("number_of_spots"));
        return reservation;
    }
}
