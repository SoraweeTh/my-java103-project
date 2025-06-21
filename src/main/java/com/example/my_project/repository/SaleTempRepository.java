package com.example.my_project.repository;

import com.example.my_project.entity.SaleTempEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SaleTempRepository extends JpaRepository<SaleTempEntity, Long> {
    List<SaleTempEntity> findAllByUserIdOrderByIdAsc(Long userId);
    SaleTempEntity findByProductionIdAndUserId(Long productionId, Long userId);

    @Modifying
    @Transactional
    void deleteAllByUserId(Long userId);
}
