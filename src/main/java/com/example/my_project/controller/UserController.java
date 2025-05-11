package com.example.my_project.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.my_project.dto.UserResponse;
import com.example.my_project.entity.UserEntity;
import com.example.my_project.repository.UserRepository;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private static final long EXPIRATION_TIME = 60 * 60 * 1000 * 24 * 7; // 1 week

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

    private String getSecret() {
        Dotenv dotenv = Dotenv.configure()
                .directory(System.getProperty("user.dir"))
                .load();
        return dotenv.get("JWT_SECRET");
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(getSecret());
    }

    @PostMapping("/admin-signin")
    public String adminSignin(@RequestBody UserEntity user) {
        try {
            String username = user.getUsername();
            String password = user.getPassword();
            UserEntity userForCreateToken = userRepository.findByUsernameAndPassword(username, password);
            return JWT.create()
                    .withSubject(String.valueOf(userForCreateToken.getId()))
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .withIssuedAt(new Date())
                    .sign(getAlgorithm());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Error creating token");
        }
    }

    @GetMapping("/admin-info")
    public UserResponse adminInfo(@RequestHeader("Authorization") String token) {
        try {
            if (token == null || !token.startsWith("Bearer ")) {
                throw new IllegalArgumentException("Invalid token format with 'Bearer '");
            }

            String tokenWithoutBearer = token.replace("Bearer ", "");
            if (tokenWithoutBearer.trim().isEmpty()) {
                throw new IllegalArgumentException("Token is empty");
            }

            String subject = JWT.require(getAlgorithm())
                    .build()
                    .verify(tokenWithoutBearer)
                    .getSubject();
            Long userId = Long.valueOf(subject);
            UserEntity user = userRepository.findById(userId).orElse(null);
            if (user == null) {
                throw new IllegalArgumentException("User not found");
            }

            return new UserResponse(user.getId(), user.getUsername(), user.getEmail());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Authorization error: " + e.getMessage());
        }
    }
}
