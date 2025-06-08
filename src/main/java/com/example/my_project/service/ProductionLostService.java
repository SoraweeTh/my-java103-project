package com.example.my_project.service;

import com.example.my_project.entity.ProductionLostEntity;
import com.example.my_project.repository.ProductionLostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductionLostService {

    private final ProductionLostRepository productionLostRepository;

    public ProductionLostService(ProductionLostRepository productionLostRepository) {
        this.productionLostRepository = productionLostRepository;
    }

    public List<ProductionLostEntity> getAllByProductionId(Long productionId) {
        return productionLostRepository.findAllByProductionIdOrderByIdDesc(productionId);
    }

    public ProductionLostEntity addProductionLost(ProductionLostEntity productionLost) {
        return productionLostRepository.save(productionLost);
    }

    public ProductionLostEntity modifyProductionLost(Long id, ProductionLostEntity productionLost) {
        ProductionLostEntity productionLostToMod = productionLostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Production lost not found."));
        productionLostToMod.setProduction(productionLost.getProduction());
        productionLostToMod.setQuantity(productionLost.getQuantity());
        productionLostToMod.setRemark(productionLost.getRemark());
        return productionLostRepository.save(productionLostToMod);
    }

    public void deleteProductionLost(Long id) {
        productionLostRepository.deleteById(id);
    }
}
