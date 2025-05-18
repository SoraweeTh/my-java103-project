package com.example.my_project.controller;

import com.example.my_project.entity.ProductionEntity;
import com.example.my_project.service.ProductionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productions")
public class ProductionController {

    private final ProductionService productionService;

    public ProductionController(ProductionService productionService) {
        this.productionService = productionService;
    }

    @GetMapping
    public List<ProductionEntity> getAllProduction() {
        return productionService.getAllProductions();
    }

    @GetMapping("/{id}")
    public ProductionEntity getProductionById(@PathVariable Long id) {
        return productionService.getProductionById(id);
    }

    @PostMapping()
    public ProductionEntity createProduction(@RequestBody ProductionEntity product) {
        return productionService.createProduction(product);
    }

    @PutMapping("/{id}")
    public ProductionEntity updateProduction(@PathVariable Long id,
                                             @RequestBody ProductionEntity product) {
        return productionService.updateProduction(id, product);
    }

    @DeleteMapping("/{id}")
    public void deleteProductionById(@PathVariable Long id) {
        productionService.deleteProductionById(id);
    }
}
