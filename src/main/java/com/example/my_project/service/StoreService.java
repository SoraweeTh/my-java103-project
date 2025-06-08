package com.example.my_project.service;

import com.example.my_project.entity.StoreEntity;
import com.example.my_project.repository.StoreRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public List<StoreEntity> getAll() {
        return storeRepository.findAll();
    }

    public StoreEntity createStore(StoreEntity store) {
        return storeRepository.save(store);
    }

    public StoreEntity updateStoreById(Long id, StoreEntity store) {
        StoreEntity storeToUpdate = storeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Store not found."));
        storeToUpdate.setName(store.getName());
        storeToUpdate.setAddress(store.getAddress());
        storeToUpdate.setRemark(store.getRemark());
        return storeRepository.save(storeToUpdate);
    }

    public void deleteStoreById(Long id) {
        storeRepository.deleteById(id);
    }

    public Map<String, Object> getProductionForImport(Long id) {
        List<Object[]> rows = storeRepository.findProductionSummary(id);
        Object[] row = rows.get(0);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("id", row[0]);
        resultMap.put("name", row[1]);
        resultMap.put("totalProductionLog", row[2]);
        resultMap.put("totalProductionLost", row[3]);

        return resultMap;
    }
}
