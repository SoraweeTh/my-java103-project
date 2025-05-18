package com.example.my_project.repository;

import com.example.my_project.entity.ProductionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductionRepository extends JpaRepository<ProductionEntity, Long> {
}
