package com.example.my_project.repository;

import com.example.my_project.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsernameAndEmail(String username, String email);
    UserEntity findByUsernameAndPassword(String username, String password);
}
