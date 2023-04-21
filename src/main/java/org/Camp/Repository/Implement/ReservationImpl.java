package org.Camp.Repository.Implement;

import org.Camp.Model.Mapper.ReservationMapper;
import org.Camp.Model.Entities.Reservation;
import org.Camp.Model.Mapper.ReservationRequestMapper;
import org.Camp.Model.Request.ReservationRequest;
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
    public List<ReservationRequest> findAll() {
        try {
            String sql = "SELECT r.id, u.name as user_name, c.name as camp_name, r.start_date, r.end_date, r.checked_out, r.number_of_spots " +
                    "FROM reservations r " +
                    "JOIN users u ON r.user_id = u.id " +
                    "JOIN camps c ON r.camp_id = c.id";
            return jdbcTemplate.query(sql, new ReservationRequestMapper());
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<ReservationRequest> findByUserNameAndCampName(String userName, String campName, boolean checkedOut) {
        try {
            String sql = "SELECT r.id, u.name as user_name, c.name as camp_name, r.start_date, r.end_date, r.checked_out, r.number_of_spots " +
                    "FROM reservations r " +
                    "JOIN users u ON r.user_id = u.id " +
                    "JOIN camps c ON r.camp_id = c.id " +
                    "WHERE u.name = ? AND c.name = ? AND checked_out= ?";
            return jdbcTemplate.query(sql, new ReservationRequestMapper(), userName, campName, checkedOut);
        } catch (Exception e) {
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
    public List<Reservation> findByUserIdAndCampId(Long userId, Long campId) {
        try {
            String sql = "SELECT * FROM reservations WHERE user_id = ? AND camp_id = ?";
            return jdbcTemplate.query(sql, new ReservationMapper(), userId, campId);
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
