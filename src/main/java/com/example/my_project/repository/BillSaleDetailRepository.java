package com.example.my_project.repository;

import com.example.my_project.entity.BillSaleDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillSaleDetailRepository extends JpaRepository<BillSaleDetailEntity, Long> {
    List<BillSaleDetailEntity> findAllByBillSaleId(Long id);
}
