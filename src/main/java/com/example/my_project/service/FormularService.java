package com.example.my_project.service;

import com.example.my_project.entity.FormularEntity;
import com.example.my_project.repository.FormularRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormularService {

    private final FormularRepository formularRepository;

    public FormularService(FormularRepository formularRepository) {
        this.formularRepository = formularRepository;
    }

    public List<FormularEntity> getAllFormulars(Long productId) {
        return formularRepository.findAllByProductionIdOrderByIdDesc(productId);
    }

    public FormularEntity createFormular(FormularEntity formular) {
        return formularRepository.save(formular);
    }

    public FormularEntity updateFormular(Long id, FormularEntity formular) {
        FormularEntity formularToUpdate = formularRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Formular not found."));
        formularToUpdate.setQuantity(formular.getQuantity());
        formularToUpdate.setUnit(formular.getUnit());
        formularToUpdate.setMaterial(formular.getMaterial());
        formularToUpdate.setProduction(formularToUpdate.getProduction());

        return formularRepository.save(formularToUpdate);
    }

    public void deleteFormular(Long id) {
        formularRepository.deleteById(id);
    }
}
