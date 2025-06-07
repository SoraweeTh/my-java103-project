package com.example.my_project.repository;

import com.example.my_project.entity.ProductionLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductionLogRepository extends JpaRepository<ProductionLogEntity, Long> {
    List<ProductionLogEntity> findAllByProductionIdOrderByIdDesc(Long productionId);
}
