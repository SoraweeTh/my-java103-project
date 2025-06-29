package com.example.my_project.repository;

import com.example.my_project.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findAllByOrderByRole();
    UserEntity findByUsernameAndEmail(String username, String email);
    UserEntity findByUsernameAndPassword(String username, String password);
}
