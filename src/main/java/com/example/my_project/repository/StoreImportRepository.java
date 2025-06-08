package com.example.my_project.repository;

import com.example.my_project.entity.StoreImportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreImportRepository extends JpaRepository<StoreImportEntity, Long> {
    List<StoreImportEntity> findByStoreId(Long storeId);
}
