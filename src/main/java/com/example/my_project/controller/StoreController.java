package com.example.my_project.controller;

import com.example.my_project.entity.StoreEntity;
import com.example.my_project.service.StoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/store")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
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
}
