package com.example.my_project.repository;

import com.example.my_project.entity.BillSaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillSaleRepository extends JpaRepository<BillSaleEntity, Long> {
}
