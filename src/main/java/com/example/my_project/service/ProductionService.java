package com.example.my_project.service;

import com.example.my_project.entity.ProductionEntity;
import com.example.my_project.repository.ProductionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductionService {

    private final  ProductionRepository productionRepository;

    public ProductionService(ProductionRepository productionRepository) {
        this.productionRepository = productionRepository;
    }

    public List<ProductionEntity> getAllProductions() {
        return productionRepository.findAll();
    }

    public ProductionEntity getProductionById(Long id) {
        return productionRepository.findById(id).orElse(null);
    }

    public ProductionEntity createProduction(ProductionEntity production) {
        try {
            return productionRepository.save(production);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Create production error : " + e.getMessage());
        }
    }

    public ProductionEntity updateProduction(Long id, ProductionEntity product) {
        try {
            ProductionEntity productionToUpdate = productionRepository.findById(id).orElse(null);
            if (productionToUpdate == null) {
                throw new IllegalArgumentException("Production not found.");
            }
            productionToUpdate.setName(product.getName());
            productionToUpdate.setDetail(product.getDetail());
            productionToUpdate.setPrice(product.getPrice());

            return productionRepository.save(productionToUpdate);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Update production id " + id + " error : " + e.getMessage());
        }
    }

    public void deleteProductionById(Long id) {
        productionRepository.deleteById(id);
    }

    public void updateProductPrice(Long id, ProductionEntity production) {
        ProductionEntity productionToUpdatePrice = productionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductionId not found."));
        productionToUpdatePrice.setPrice(production.getPrice());
        productionRepository.save(productionToUpdatePrice);
    }
}
