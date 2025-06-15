package com.example.my_project.service;

import com.example.my_project.entity.TransferStockEntity;
import com.example.my_project.repository.TransferStockRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferStockService {

    private final TransferStockRepository transferStockRepository;

    public TransferStockService(TransferStockRepository transferStockRepository) {
        this.transferStockRepository = transferStockRepository;
    }

    public List<TransferStockEntity> listStock() {
        return transferStockRepository.findAll();
    }

    public TransferStockEntity createStock(TransferStockEntity transferStock) {
        return transferStockRepository.save(transferStock);
    }

    public TransferStockEntity updateStock(Long id, TransferStockEntity transferStock) {
        TransferStockEntity tsToUpdate = transferStockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TransferStock not found"));
        tsToUpdate.setFromStore(transferStock.getFromStore());
        tsToUpdate.setToStore(transferStock.getToStore());
        tsToUpdate.setProduction(transferStock.getProduction());
        tsToUpdate.setQuantity(transferStock.getQuantity());
        tsToUpdate.setRemark(transferStock.getRemark());
        tsToUpdate.setCreatedAt(transferStock.getCreatedAt());
        return transferStockRepository.save(tsToUpdate);
    }

    public void deleteStock(Long id) {
        transferStockRepository.deleteById(id);
    }
}
