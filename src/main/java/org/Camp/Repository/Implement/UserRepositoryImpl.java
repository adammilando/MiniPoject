package org.Camp.Repository.Implement;

import org.Camp.Model.Mapper.UserMapper;
import org.Camp.Model.Entities.User;
import org.Camp.Repository.UserRepository;
import org.Camp.Utils.IdGenerator;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    private JdbcTemplate jdbcTemplate;
    private IdGenerator idGenerator;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate, IdGenerator idGenerator) {
        this.jdbcTemplate = jdbcTemplate;
        this.idGenerator = idGenerator;
    }

    @Override
    public List<User> findAll() {
        try {
            String sql = "SELECT * FROM users";
            return jdbcTemplate.query(sql, new UserMapper());
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        try {
            String sql = "SELECT * FROM users WHERE id = ?";
            User user = jdbcTemplate.queryForObject(sql, new UserMapper(), id);
            return Optional.ofNullable(user);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public int save(User user) {
        try {
            Long id = idGenerator.generateId();
            String sql = "INSERT INTO users (id, name, email, password) VALUES (?, ?, ?, ?)";
            return jdbcTemplate.update(sql, id, user.getName(), user.getEmail(), user.getPassword());
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public int update(User user) {
        try {
            String sql = "UPDATE users SET name = ?, email = ?, password = ? WHERE id = ?";
            return jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPassword(), user.getId());
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public int delete(Long id) {
        try {
            String sql = "DELETE FROM users WHERE id = ?";
            return jdbcTemplate.update(sql, id);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
