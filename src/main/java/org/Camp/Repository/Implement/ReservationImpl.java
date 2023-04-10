package org.Camp.Repository.Implement;

import org.Camp.Model.Mapper.ReservationMapper;
import org.Camp.Model.Entities.Reservation;
import org.Camp.Repository.ReservationRepository;
import org.Camp.Utils.IdGenerator;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

public class ReservationImpl implements ReservationRepository {

    private JdbcTemplate jdbcTemplate;
    private IdGenerator idGenerator;

    public ReservationImpl(JdbcTemplate jdbcTemplate, IdGenerator idGenerator) {
        this.jdbcTemplate = jdbcTemplate;
        this.idGenerator = idGenerator;
    }

    @Override
    public List<Reservation> findAll() {
        try {
            String sql = "SELECT * FROM reservations";
            return jdbcTemplate.query(sql, new ReservationMapper());
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        try {
            String sql = "SELECT * FROM reservations WHERE id = ?";
            Reservation reservation = jdbcTemplate.queryForObject(sql, new ReservationMapper(), id);
            return Optional.ofNullable(reservation);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException("Failed to find reservation", e);
        }
    }


    @Override
    public Optional<Reservation> findByUserIdAndCampId(Long userId, Long campId) {
        try {
            String sql = "SELECT * FROM reservations WHERE user_id = ? AND camp_id = ?";
            List<Reservation> reservations = jdbcTemplate.query(sql, new ReservationMapper(), userId, campId);
            return reservations.isEmpty() ? Optional.empty() : Optional.of(reservations.get(0));
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to find reservation", e);
        }
    }

    @Override
    public Optional<Reservation> findByCampId(Long id) {
        try {
            String sql = "SELECT * FROM reservations WHERE camp_id = ?";
            List<Reservation> reservations = jdbcTemplate.query(sql, new ReservationMapper(),id);
            return reservations.isEmpty() ? Optional.empty() : Optional.of(reservations.get(0));
        }catch (DataAccessException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public int save(Reservation reservation) {
        try {
            Long id = idGenerator.generateId();
            String sql = "INSERT INTO reservations (id,user_id, camp_id, start_date, end_date, checked_out, number_of_spots) VALUES (?, ?, ?, ?, ?, ?, ?)";
            return jdbcTemplate.update(sql, id, reservation.getUserId(), reservation.getCampId(), reservation.getStartDate(), reservation.getEndDate(), reservation.isCheckedOut(), reservation.getNumberOfSpots());
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public int update(Reservation reservation) {
        try {
            String sql = "UPDATE reservations SET user_id = ?, camp_id = ?, start_date = ?, end_date = ?, checked_out = ?, number_of_spots = ? WHERE id = ?";
            return jdbcTemplate.update(sql, reservation.getUserId(), reservation.getCampId(), reservation.getStartDate(), reservation.getEndDate(), reservation.isCheckedOut(), reservation.getNumberOfSpots(), reservation.getId());
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }


    @Override
    public int delete(Long id) {
        try {
            String sql = "DELETE FROM reservations WHERE id = ?";
            return jdbcTemplate.update(sql, id);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
