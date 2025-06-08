package com.example.my_project.controller;

import com.example.my_project.entity.StoreEntity;
import com.example.my_project.entity.StoreImportEntity;
import com.example.my_project.service.StoreImportService;
import com.example.my_project.service.StoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/store")
public class StoreController {

    private final StoreService storeService;
    private final StoreImportService storeImportService;

    public StoreController(StoreService storeService, StoreImportService storeImportService) {
        this.storeService = storeService;
        this.storeImportService = storeImportService;
    }

    @GetMapping
    public ResponseEntity<List<StoreEntity>> getAllStore() {
        return ResponseEntity.ok(storeService.getAll());
    }

    @PostMapping
    public ResponseEntity<StoreEntity> createStore(@RequestBody StoreEntity storeEntity) {
        return ResponseEntity.ok(storeService.createStore(storeEntity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StoreEntity> updateStore(
            @PathVariable Long id, @RequestBody StoreEntity storeEntity) {
        return ResponseEntity.ok(storeService.updateStoreById(id, storeEntity));
    }

    @DeleteMapping("/{id}")
    public void deleteStoreById(@PathVariable Long id) {
        storeService.deleteStoreById(id);
    }

    @GetMapping("/data-for-import/{id}")
    public ResponseEntity<Map<String, Object>> getDataForImport(@PathVariable Long id) {
        return ResponseEntity.ok(storeService.getProductionForImport(id));
    }

    @PostMapping("/import")
    public ResponseEntity<StoreImportEntity> importData(@RequestBody StoreImportEntity storeImportEntity) {
        return ResponseEntity.ok(storeImportService.importToStore(storeImportEntity));
    }

    @GetMapping("/import/{storeId}")
    public ResponseEntity<List<StoreImportEntity>> getImportDataById(@PathVariable Long storeId) {
        return ResponseEntity.ok(storeImportService.getImportHistory(storeId));
    }

    @DeleteMapping("/import/{id}")
    public void deleteImportHistory(@PathVariable Long id) {
        storeImportService.deleteImportHistory(id);
    }
}
