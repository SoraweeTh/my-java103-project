package com.example.my_project.service;

import com.example.my_project.entity.ProductionLogEntity;
import com.example.my_project.repository.ProductionLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductionLogService {

    private final ProductionLogRepository productionLogRepository;

    public ProductionLogService(ProductionLogRepository productionLogRepository) {
        this.productionLogRepository = productionLogRepository;
    }

    public List<ProductionLogEntity> getAllProductionId(Long productionId) {
        return productionLogRepository.findAllByProductionIdOrderByIdDesc(productionId);
    }

    public ProductionLogEntity createProductionLog(ProductionLogEntity productionLog) {
        return productionLogRepository.save(productionLog);
    }

    public ProductionLogEntity updateProductionLog(Long id, ProductionLogEntity productionLog) {
        ProductionLogEntity productionLogToUpdate = productionLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Production log not found"));
        productionLogToUpdate.setProduction(productionLog.getProduction());
        productionLogToUpdate.setQuantity(productionLog.getQuantity());
        productionLogToUpdate.setRemark(productionLog.getRemark());
        return productionLogRepository.save(productionLogToUpdate);
    }

    public void deleteProductionLogById(Long id) {
        productionLogRepository.deleteById(id);
    }
}
