package com.example.my_project.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.my_project.dto.UserResponse;
import com.example.my_project.entity.UserEntity;
import com.example.my_project.repository.UserRepository;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService {

    private static final long EXPIRATION_TIME = 60 * 60 * 1000 * 24 * 7; // 1 week

    @Autowired
    private UserRepository userRepository;

    private String getSecret() {
        Dotenv dotenv = Dotenv.configure()
                .directory(System.getProperty("user.dir"))
                .load();
        return dotenv.get("JWT_SECRET");
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(getSecret());
    }

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public UserEntity findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    public UserEntity updateUser(Long id, UserEntity user) {
        UserEntity userToUpdate = userRepository.findById(id).orElse(null);
        if (userToUpdate == null) {
            throw new IllegalArgumentException("User not found.");
        }
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setEmail(user.getEmail());
        return userRepository.save(userToUpdate);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserEntity signIn(UserEntity user) {
        String username = user.getUsername();
        String email = user.getEmail();
        UserEntity userToSignIn = userRepository.findByUsernameAndEmail(username, email);
        if (userToSignIn == null) {
            throw new IllegalArgumentException("User not found.");
        }
        return userToSignIn;
    }

    public String signInForAdmin(UserEntity user) {
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
            throw new IllegalArgumentException("Error creating token.");
        }
    }

    public UserResponse getAdminInfo(String token) {
        try {
            if (token == null || !token.startsWith("Bearer ")) {
                throw new IllegalArgumentException("Invalid token format with 'Bearer '.");
            }

            String tokenWithoutBearer = token.replace("Bearer ", "");
            if (tokenWithoutBearer.trim().isEmpty()) {
                throw new IllegalArgumentException("Token is empty.");
            }

            String subject = JWT.require(getAlgorithm())
                    .build()
                    .verify(tokenWithoutBearer)
                    .getSubject();
            Long userId = Long.valueOf(subject);
            UserEntity user = userRepository.findById(userId).orElse(null);
            if (user == null) {
                throw new IllegalArgumentException("User not found.");
            }

            return new UserResponse(user.getId(), user.getUsername(), user.getEmail());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Authorization error : " + e.getMessage());
        }
    }

    public Long getUserIdFromToken(String token) {
        String tokenWithoutBearer = token.replace("Bearer ", "");
        if (tokenWithoutBearer.trim().isEmpty()) {
            throw new IllegalArgumentException("Token is empty.");
        }
        return Long.valueOf(JWT.require(getAlgorithm())
                .build()
                .verify(tokenWithoutBearer)
                .getSubject());
    }

    public UserEntity editProfileAdmin(String token, UserEntity user) {
        //System.out.println("token - " + token);
        try {
            Long userId = getUserIdFromToken(token);
            UserEntity userToUpdate = userRepository.findById(userId).orElse(null);
            if (userToUpdate == null) {
                throw new IllegalArgumentException("User not found.");
            }

            userToUpdate.setUsername(user.getUsername());
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setRole(user.getRole());
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                userToUpdate.setPassword(user.getPassword());
            }

            userRepository.save(userToUpdate);
            return userToUpdate;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Update admin profile error : " + e.getMessage());
        }
    }

    public UserEntity updateAdmin(Long id, UserEntity user) {
        try {
            UserEntity userToUpdate = userRepository.findById(id).orElse(null);
            if (userToUpdate == null) {
                throw new IllegalArgumentException("User not found.");
            }
            userToUpdate.setUsername(user.getUsername());
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setRole(user.getRole());
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                userToUpdate.setPassword(user.getPassword());
            }

            userRepository.save(userToUpdate);
            return userToUpdate;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Update admin error : " + e.getMessage());
        }
    }

    public UserEntity createAdmin(String token, UserEntity user) {
        try {
            userRepository.save(user);
            return user;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Create admin error : " + e.getMessage());
        }
    }

    public void deleteAdmin(Long id) {
        try {
            //Long userId = getUserIdFromToken(token);
            UserEntity userToDelete = userRepository.findById(id).orElse(null);
            if (userToDelete == null) {
                throw new IllegalArgumentException("User not found.");
            }
            userRepository.deleteById(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Delete admin error : " + e.getMessage());
        }
    }
}
