package com.example.my_project.service;

import com.example.my_project.entity.StoreImportEntity;
import com.example.my_project.repository.StoreImportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreImportService {

    private final StoreImportRepository storeImportRepository;

    public StoreImportService(StoreImportRepository storeImportRepository) {
        this.storeImportRepository = storeImportRepository;
    }

    public StoreImportEntity importToStore(StoreImportEntity entity) {
        return storeImportRepository.save(entity);
    }

    public List<StoreImportEntity> getImportHistory(Long storeId) {
        return storeImportRepository.findByStoreId(storeId);
    }

    public void deleteImportHistory(Long id) {
        storeImportRepository.deleteById(id);
    }
}
