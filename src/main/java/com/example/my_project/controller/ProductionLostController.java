package com.example.my_project.controller;

import com.example.my_project.entity.ProductionLostEntity;
import com.example.my_project.service.ProductionLostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/production-lost")
public class ProductionLostController {

    private final ProductionLostService productionLostService;

    public ProductionLostController(ProductionLostService productionLostService) {
        this.productionLostService = productionLostService;
    }

    @GetMapping("/{productionId}")
    public ResponseEntity<List<ProductionLostEntity>> getAllByProductionId(@PathVariable Long productionId) {
        return ResponseEntity.ok(productionLostService.getAllByProductionId(productionId));
    }

    @PostMapping
    public ResponseEntity<ProductionLostEntity> createProductionLost(@RequestBody ProductionLostEntity productionLost) {
        return ResponseEntity.ok(productionLostService.addProductionLost(productionLost));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductionLostEntity> updateProductionLost(
            @PathVariable Long id, @RequestBody ProductionLostEntity productionLost) {
        return ResponseEntity.ok(productionLostService.modifyProductionLost(id, productionLost));
    }

    @DeleteMapping("/{id}")
    public void deleteProductionLost(@PathVariable Long id) {
        productionLostService.deleteProductionLost(id);
    }
}
