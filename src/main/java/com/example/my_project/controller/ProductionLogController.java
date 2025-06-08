package com.example.my_project.controller;

import com.example.my_project.entity.ProductionLogEntity;
import com.example.my_project.service.ProductionLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/production-logs")
public class ProductionLogController {

    private final ProductionLogService productionLogService;

    public ProductionLogController(ProductionLogService productionLogService) {
        this.productionLogService = productionLogService;
    }

    @GetMapping("/{productionId}")
    public ResponseEntity<List<ProductionLogEntity>> getAllProductionLog(@PathVariable Long productionId) {
        return ResponseEntity.ok(productionLogService.getAllProductionId(productionId));
    }

    @PostMapping
    public ResponseEntity<ProductionLogEntity> createProductionLog(
            @RequestBody ProductionLogEntity productionLog) {
        return ResponseEntity.ok(productionLogService.createProductionLog(productionLog));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductionLogEntity> updateProductionLog(
            @PathVariable Long id, @RequestBody ProductionLogEntity productionLog
    ) {
        return ResponseEntity.ok(productionLogService.updateProductionLog(id, productionLog));
    }

    @DeleteMapping("/{id}")
    public void deleteProductionLog(@PathVariable Long id) {
        productionLogService.deleteProductionLogById(id);
    }
}
