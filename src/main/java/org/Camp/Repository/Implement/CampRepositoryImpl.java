package org.Camp.Repository.Implement;

import org.Camp.Model.Entities.Camp;
import org.Camp.Model.Mapper.CampMapper;
import org.Camp.Repository.CampRepository;
import org.Camp.Utils.IdGenerator;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

public class CampRepositoryImpl implements CampRepository {

    private JdbcTemplate jdbcTemplate;
    private IdGenerator idGenerator;

    public CampRepositoryImpl(JdbcTemplate jdbcTemplate, IdGenerator idGenerator) {
        this.jdbcTemplate = jdbcTemplate;
        this.idGenerator = idGenerator;
    }

    @Override
    public List<Camp> findAll() {
        try {
            String sql= "SELECT * FROM camps";
            return jdbcTemplate.query(sql, new CampMapper());
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Optional<Camp> findById(Long id) {
        try {
            String sql= "SELECT * FROM camps WHERE id = ?";
            Camp camp = jdbcTemplate.queryForObject(sql, new CampMapper(),id);
            return Optional.ofNullable(camp);
        }catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public int save(Camp camp) {
        try {
            Long id = idGenerator.generateId();
            String sql = "INSERT INTO camps (id, name, location, price, stock) VALUES (?, ?, ?, ?, ?)";
            return jdbcTemplate.update(sql, id, camp.getName(), camp.getLocation(), camp.getPrice(), camp.getStock());
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public int update(Camp camp) {
        try {
            String sql = "UPDATE camps SET name = ?, location = ?, price = ?, stock = ? WHERE id = ?";
            return jdbcTemplate.update(sql, camp.getName(), camp.getLocation(), camp.getPrice(), camp.getStock(), camp.getId());
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public int delete(Long id) {
        try {
            String sql = "DELETE FROM camps WHERE id = ?";
            return jdbcTemplate.update(sql, id);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
