package com.example.my_project.repository;

import com.example.my_project.entity.ProductionLostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductionLostRepository extends JpaRepository<ProductionLostEntity, Long> {
    List<ProductionLostEntity> findAllByProductionIdOrderByIdDesc(Long productionId);
}
