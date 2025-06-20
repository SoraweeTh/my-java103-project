package com.example.my_project.controller;

import com.example.my_project.entity.SaleTempEntity;
import com.example.my_project.service.SaleTempService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saleTemp")
public class SaleTempController {

    private final SaleTempService saleTempService;

    public SaleTempController(SaleTempService saleTempService) {
        this.saleTempService = saleTempService;
    }

    @PostMapping
    public ResponseEntity<SaleTempEntity> createSaleTemp(@RequestHeader("Authorization") String token,
                                                         @RequestBody SaleTempEntity saleTempEntity) {
        return ResponseEntity.ok(saleTempService.createSaleTemp(saleTempEntity, token));
    }

    @GetMapping
    public ResponseEntity<List<SaleTempEntity>> getAll(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(saleTempService.getAll(token));
    }

    @DeleteMapping("/{id}")
    public void deleteSaleTemp(@RequestHeader("Authorization") String token,
                               @PathVariable Long id) {
        saleTempService.deleteSaleTemp(token, id);
    }

    @PutMapping("/{id}")
    public void updateSaleTempQuantity(@PathVariable Long id, @RequestBody SaleTempEntity saleTemp) {
        saleTempService.updateSaleTempQuantity(id, saleTemp);
    }
}
