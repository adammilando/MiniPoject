package org.Camp.Service.Implement;

import org.Camp.Exception.NotFoundException;
import org.Camp.Exception.UserException;
import org.Camp.Model.Entities.User;
import org.Camp.Repository.UserRepository;
import org.Camp.Service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

import java.util.List;
import java.util.regex.Pattern;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    private final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-zA-Z]).{8,}$");

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        try {
            List<User> users = userRepository.findAll();
            if (users.isEmpty()){
                throw new NotFoundException("Database Empty");
            }
            return users;
        } catch (NotFoundException e){
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to find all users: " + e.getMessage(), e);
        }
    }

    @Override
    public User findById(Long id) {
        try {
            return userRepository.findById(id).orElseThrow(()-> new NotFoundException("Id Not Found"));
        }catch (NotFoundException e){
            throw e;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public User save(User user) {
        try {
            validateUser(user);
            validateUserDetails(user);
            String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
            user.setPassword(encodedPassword);
            return userRepository.save(user) == 1 ? user : null;
        }catch (UserException e){
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save user: " + e.getMessage(), e);
        }
    }

    @Override
    public User update(User user) {
        try {
            findById(user.getId());
            validateUser(user);
            validateUserDetails(user);
            String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
            user.setPassword(encodedPassword);
            return userRepository.update(user) == 1 ? user : null;
        } catch (UserException e){
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update user: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            findById(id);
            return userRepository.delete(id) == 1;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete user: " + e.getMessage(), e);
        }
    }

    private void validateUserDetails(User user) {
        List<User> allUsers = userRepository.findAll();

        allUsers.forEach(existingUser -> {
            if (existingUser.getName().equalsIgnoreCase(user.getName()) && !existingUser.getId().equals(user.getId())) {
                throw new UserException("Username already exists");
            } else if (existingUser.getEmail().equalsIgnoreCase(user.getEmail()) && !existingUser.getId().equals(user.getId())) {
                throw new UserException("Email already exists");
            }
        });
    }



    private void validateUser(User user) {
        try {
            Assert.notNull(user, "User must not be null");
            Assert.notNull(user.getName(), "Name must not be null");
            Assert.isTrue(user.getName().length() >= 1, "Name must not be empty");
            Assert.notNull(user.getEmail(), "Email must not be null");
            Assert.isTrue(EMAIL_PATTERN.matcher(user.getEmail()).matches(), "Email must be a valid email address");
            Assert.notNull(user.getPassword(), "Password must not be null");
            Assert.isTrue(user.getPassword().length() >= 8, "Password must be at least 8 characters long");
            Assert.isTrue(PASSWORD_PATTERN.matcher(user.getPassword()).matches(), "Password must contain at least one number, one symbol, and one letter");
        }catch (IllegalArgumentException e){
            throw new UserException(e.getMessage());
        }
    }
}
