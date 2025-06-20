package com.example.my_project.service;

import com.example.my_project.entity.ProductionEntity;
import com.example.my_project.entity.SaleTempEntity;
import com.example.my_project.repository.ProductionRepository;
import com.example.my_project.repository.SaleTempRepository;
import org.springframework.stereotype.Service;

import java.nio.BufferUnderflowException;
import java.util.List;

@Service
public class SaleTempService {

    private final SaleTempRepository saleTempRepository;
    private final ProductionRepository productionRepository;

    public SaleTempService(SaleTempRepository saleTempRepository,
                           ProductionRepository productionRepository) {
        this.saleTempRepository = saleTempRepository;
        this.productionRepository = productionRepository;
    }

    public SaleTempEntity createSaleTemp(SaleTempEntity saleTemp, String token) {
        UserService user = new UserService();
        Long userId = user.getUserIdFromToken(token);

        Long productionId = saleTemp.getProduction().getId();
        ProductionEntity production = productionRepository.findById(productionId)
                .orElseThrow(() -> new RuntimeException("Production not found."));

        SaleTempEntity oldSaleTemp = saleTempRepository.findByProductionIdAndUserId(productionId, userId);

        // update record
        if (oldSaleTemp != null) {
            oldSaleTemp.setQuantity(oldSaleTemp.getQuantity() + 1);
            return saleTempRepository.save(oldSaleTemp);
        }

        // new record
        if (production != null) {
            saleTemp.setQuantity(1);
            saleTemp.setUserId(userId.intValue());
            saleTemp.setPrice(production.getPrice());
            saleTemp.setProduction(production);
        }

        return saleTempRepository.save(saleTemp);
    }

    public List<SaleTempEntity> getAll(String token) {
        UserService user = new UserService();
        Long userId = user.getUserIdFromToken(token);
        return saleTempRepository.findAllByUserIdOrderByIdAsc(userId);
    }

    public void deleteSaleTemp(String token, Long id) {
        UserService user = new UserService();
        Long userId = user.getUserIdFromToken(token);

        if (userId == null) {
            throw new RuntimeException("User not found.");
        }

        saleTempRepository.deleteById(id);
    }

    public void updateSaleTempQuantity(Long id, SaleTempEntity saleTemp) {
        SaleTempEntity saleTempToUpdate = saleTempRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SaleTemp not found."));
        saleTempToUpdate.setQuantity(saleTemp.getQuantity());
        saleTempRepository.save(saleTempToUpdate);
    }
}
