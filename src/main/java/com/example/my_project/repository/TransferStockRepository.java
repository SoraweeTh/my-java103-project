package com.example.my_project.repository;

import com.example.my_project.entity.TransferStockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransferStockRepository extends JpaRepository<TransferStockEntity, Long> {
    List<TransferStockEntity> findByFromStoreId(Long fromStoreId);
    List<TransferStockEntity> findByToStoreId(Long toStoreId);
}
