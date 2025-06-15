package com.example.my_project.controller;

import com.example.my_project.entity.TransferStockEntity;
import com.example.my_project.service.TransferStockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transfer-stock")
public class TransferStockController {

    private final TransferStockService transferStockService;

    public TransferStockController(TransferStockService transferStockService) {
        this.transferStockService = transferStockService;
    }

    @GetMapping
    public ResponseEntity<List<TransferStockEntity>> getAll() {
        return ResponseEntity.ok(transferStockService.listStock());
    }

    @PostMapping
    public ResponseEntity<TransferStockEntity> createStock(@RequestBody TransferStockEntity transferStock) {
        return ResponseEntity.ok(transferStockService.createStock(transferStock));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransferStockEntity> updateStock(@PathVariable Long id,
                                                           @RequestBody TransferStockEntity transferStock) {
        return ResponseEntity.ok(transferStockService.updateStock(id, transferStock));
    }

    @DeleteMapping("/{id}")
    public void deleteStock(@PathVariable Long id) {
        transferStockService.deleteStock(id);
    }
}
