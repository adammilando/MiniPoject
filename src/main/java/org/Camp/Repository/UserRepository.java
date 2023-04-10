package org.Camp.Repository;

import org.Camp.Model.Entities.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();
    Optional<User> findById(Long id);
    int save(User user);
    int update(User user);
    int delete(Long id);
}
