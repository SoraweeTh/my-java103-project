package com.example.my_project.controller;

import com.example.my_project.entity.FormularEntity;
import com.example.my_project.service.FormularService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/formulars")
public class FormularController {

    private final FormularService formularService;

    public FormularController(FormularService formularService) {
        this.formularService = formularService;
    }

    @GetMapping("/{productId}")
    public List<FormularEntity> getAllFormulars(@PathVariable Long productId) {
        return formularService.getAllFormulars(productId);
    }

    @PostMapping
    public FormularEntity createFormular(@RequestBody FormularEntity formular) {
        return formularService.createFormular(formular);
    }

    @PutMapping("/{id}")
    public FormularEntity updateFormular(@PathVariable Long id, @RequestBody FormularEntity formular) {
        return formularService.updateFormular(id, formular);
    }

    @DeleteMapping("/{id}")
    public void deleteFormular(@PathVariable Long id) {
        formularService.deleteFormular(id);
    }
}
