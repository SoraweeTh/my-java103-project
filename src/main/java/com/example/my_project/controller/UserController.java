package com.example.my_project.controller;

import com.example.my_project.annotation.RequireAuth;
import com.example.my_project.dto.UserResponse;
import com.example.my_project.entity.UserEntity;
import com.example.my_project.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @RequireAuth
    public List<UserEntity> getAllUser() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserEntity getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping
    public UserEntity createUser(@RequestBody UserEntity user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public UserEntity updateUser(@PathVariable Long id, @RequestBody UserEntity user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PostMapping("/signin")
    public UserEntity signIn(@RequestBody UserEntity user) {
        return userService.signIn(user);
    }

    @PostMapping("/admin-signin")
    public Object adminSignIn(@RequestBody UserEntity user) {
        return userService.signInForAdmin(user);
    }

    @GetMapping("/admin-info")
    public UserResponse adminInfo(@RequestHeader("Authorization") String token) {
        return userService.getAdminInfo(token);
    }

    @PostMapping("/admin-edit-profile")
    public UserEntity adminEditProfile(@RequestHeader("Authorization") String token,
                                       @RequestBody UserEntity user) {
        return userService.editProfileAdmin(token, user);
    }

    @PostMapping("/admin-update/{id}")
    public UserEntity adminUpdate(@PathVariable Long id, @RequestBody UserEntity user) {
        return userService.updateAdmin(id, user);
    }

    @PostMapping("/admin-create")
    public UserEntity adminCreate(@RequestHeader("Authorization") String token,
                                  @RequestBody UserEntity user) {
        return userService.createAdmin(token, user);
    }

    @DeleteMapping("/admin-delete/{id}")
    public void adminDelete(@RequestHeader("Authorization") String token,
                            @PathVariable Long id) {
        userService.deleteAdmin(id);
    }
}
