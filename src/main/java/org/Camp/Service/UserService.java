package org.Camp.Service;

import org.Camp.Model.Entities.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(Long id);
    User save(User user);
    User update(User user);
    boolean delete(Long id);
}
