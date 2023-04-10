package org.Camp.Controller;

import org.Camp.Model.Response.CommonResponse;
import org.Camp.Model.Response.SuccessResponse;
import org.Camp.Model.Entities.User;
import org.Camp.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/users")
public class UserApiController {

    private final UserService userService;

    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity getAllUsers() {
        List<User> users = userService.findAll();
        CommonResponse commonResponse = new SuccessResponse<>("Success Get All User", users);
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable("id") Long id) {
        User user = userService.findById(id);
        CommonResponse commonResponse = new SuccessResponse<>("Success Find User With id: "+id,user);
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody User user) {
        User createdUser = userService.save(user);
        CommonResponse commonResponse = new SuccessResponse<>("Success Creating New User",user);
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        user.setId(id);
        User updatedUser = userService.update(user);
        CommonResponse commonResponse = new SuccessResponse<>("Success Updating User With id: "+id,updatedUser);
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Long id) {
        User user= userService.findById(id);
        userService.delete(id);
        CommonResponse commonResponse = new SuccessResponse<>("Success Deleting User with id: "+id,user);
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
}
