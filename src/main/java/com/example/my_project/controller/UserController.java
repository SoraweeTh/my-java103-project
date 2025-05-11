package com.example.my_project.controller;

import com.example.my_project.entity.UserEntity;
import com.example.my_project.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<UserEntity> getAllUser() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public UserEntity getUserById(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @PostMapping
    public UserEntity createUser(@RequestBody UserEntity user) {
        return userRepository.save(user);
    }

    @PutMapping("/{id}")
    public UserEntity updateUser(@PathVariable Long id, @RequestBody UserEntity user) {
        UserEntity userToUpdate = userRepository.findById(id).orElse(null);
        if (userToUpdate == null) {
            throw new IllegalArgumentException("User not found.");
        }

        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setEmail(user.getEmail());
        return userRepository.save(userToUpdate);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

    @PostMapping("/signin")
    public UserEntity signIn(@RequestBody UserEntity user) {
        String username = user.getUsername();
        String email = user.getEmail();
        UserEntity userToSignIn = userRepository.findByUsernameAndEmail(username, email);
        if (userToSignIn == null) {
            throw new IllegalArgumentException("User not found.");
        }
        return userToSignIn;
    }
}
