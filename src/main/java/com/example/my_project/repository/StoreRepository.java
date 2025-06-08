package com.example.my_project.repository;

import com.example.my_project.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreRepository extends JpaRepository<StoreEntity, Long> {
    String SQL = """
            SELECT 
                id, 
                name, 
                (SELECT SUM(production_log_entity.quantity) FROM production_log_entity
                 WHERE production_log_entity.production_id = production_entity.id
                ) AS total_production_log,
                (SELECT SUM(production_lost_entity.quantity) FROM production_lost_entity
                 WHERE production_lost_entity.production_id = production_entity.id
                ) AS total_production_lost
            FROM production_entity
            WHERE production_entity.id = :id;
            """;

    @Query(value = SQL, nativeQuery = true)
    List<Object[]> findProductionSummary(@Param("id") Long id);
}
