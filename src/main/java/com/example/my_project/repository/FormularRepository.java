package com.example.my_project.repository;

import com.example.my_project.entity.FormularEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FormularRepository extends JpaRepository<FormularEntity, Long> {
    List<FormularEntity> findAllByProductionIdOrderByIdDesc(Long productId);
}
