package com.example.my_project.controller;

import com.example.my_project.entity.MaterialEntity;
import com.example.my_project.service.MaterialService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materials")
public class MaterialController {

    private final MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @GetMapping
    public ResponseEntity<List<MaterialEntity>> getAllMaterial() {
        List<MaterialEntity> materialList = materialService.getAllMaterial();
        return ResponseEntity.ok(materialList);
    }

    @PostMapping
    public ResponseEntity<MaterialEntity> createMaterial(@RequestBody MaterialEntity entity) {
        MaterialEntity material = materialService.createMaterial(entity);
        return ResponseEntity.ok(material);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaterialEntity> updateMaterial(@PathVariable Long id,
                                                         @RequestBody MaterialEntity entity) {
        MaterialEntity material = materialService.updateMaterial(id, entity);
        return ResponseEntity.ok(material);
    }

    @DeleteMapping("/{id}")
    public void deleteMaterial(@PathVariable Long id) {
        materialService.deleteMaterial(id);
    }
}
