package com.example.my_project.service;

import com.example.my_project.entity.MaterialEntity;
import com.example.my_project.repository.MaterialRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialService {

    private final MaterialRepository materialRepository;

    public MaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    public List<MaterialEntity> getAllMaterial() {
        return materialRepository.findByOrderByIdDesc();
    }

    public MaterialEntity createMaterial(MaterialEntity material) {
        try {
            return materialRepository.save(material);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Create material error : " + e.getMessage());
        }
    }

    public MaterialEntity updateMaterial(Long id, MaterialEntity material) {
        try {
            MaterialEntity materialToUpdate = materialRepository.findById(id).orElse(null);
            if (materialToUpdate == null) {
                throw new IllegalArgumentException("Material not found.");
            }
            materialToUpdate.setName(material.getName());
            materialToUpdate.setUnitName(material.getUnitName());
            materialToUpdate.setQuantity(material.getQuantity());
            return materialRepository.save(materialToUpdate);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Update material error : " + e.getMessage());
        }
    }

    public void deleteMaterial(Long id) {
        materialRepository.deleteById(id);
    }
}
