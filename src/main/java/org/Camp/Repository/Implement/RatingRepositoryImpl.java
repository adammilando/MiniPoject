package org.Camp.Repository.Implement;

import org.Camp.Model.Mapper.RatingMapper;
import org.Camp.Model.Entities.Rating;
import org.Camp.Model.Mapper.RatingRequestMapper;
import org.Camp.Model.Request.RatingRequest;
import org.Camp.Repository.RatingRepository;
import org.Camp.Utils.IdGenerator;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

public class RatingRepositoryImpl implements RatingRepository {

    private final JdbcTemplate jdbcTemplate;
    private final IdGenerator idGenerator;

    public RatingRepositoryImpl(JdbcTemplate jdbcTemplate, IdGenerator idGenerator) {
        this.jdbcTemplate = jdbcTemplate;
        this.idGenerator = idGenerator;
    }

    @Override
    public List<RatingRequest> findAll() {
        try {
            String sql = "SELECT rt.id, u.name AS user_name, c.name AS camp_name, rt.score, rt.comment "+
                    "FROM ratings rt " +
                    "JOIN reservations res ON rt.reservation_id = res.id " +
                    "JOIN users u ON res.user_id = u.id " +
                    "JOIN camps c ON res.camp_id = c.id ";
            return jdbcTemplate.query(sql, new RatingRequestMapper());
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<RatingRequest> findByUserAndCamp(String user, String camp) {
        try {
            String sql = "SELECT rt.id, u.name AS user_name, c.name AS camp_name, rt.score, rt.comment "+
                    "FROM ratings rt " +
                    "JOIN reservations res ON rt.reservation_id = res.id " +
                    "JOIN users u ON res.user_id = u.id " +
                    "JOIN camps c ON res.camp_id = c.id " +
                    "WHERE u.name = ? AND c.name = ?";
            return jdbcTemplate.query(sql, new RatingRequestMapper(), user, camp);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Optional<Rating> findById(Long id) {
        try {
            String sql = "SELECT * FROM ratings WHERE id = ?";
            List<Rating> ratings = jdbcTemplate.query(sql, new RatingMapper(), id);
            if (ratings.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.of(ratings.get(0));
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Optional<Rating> findByReservationId(Long reservationId) {
        try {
            String sql = "SELECT * FROM ratings WHERE reservation_id = ?";
            List<Rating> ratings = jdbcTemplate.query(sql, new RatingMapper(), reservationId);
            return ratings.isEmpty() ? Optional.empty() : Optional.of(ratings.get(0));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    @Override
    public int save(Rating rating) {
        try {
            Long id = idGenerator.generateId();
            String sql = "INSERT INTO ratings (id, reservation_id,score, comment) VALUES (?, ?, ?, ?)";
            return jdbcTemplate.update(sql, id, rating.getReservationId(), rating.getScore(), rating.getComment());
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public int update(Rating rating) {
        try {
            String sql = "UPDATE ratings SET score = ?, comment = ? WHERE id = ?";
            return jdbcTemplate.update(sql, rating.getScore(), rating.getComment(), rating.getId());
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public int delete(Long id) {
        try {
            String sql = "DELETE FROM ratings WHERE id = ?";
            return jdbcTemplate.update(sql, id);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
