package com.example.my_project.controller;

import com.example.my_project.dto.IncomePerMonth;
import com.example.my_project.entity.BillSaleDetailEntity;
import com.example.my_project.entity.BillSaleEntity;
import com.example.my_project.service.ReportService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    @PersistenceContext
    private EntityManager entityManager;

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/sum-income-per-month/{year}")
    public List<IncomePerMonth> sumIncomePerMonth(@PathVariable int year) {
        return reportService.sumIncomePerMonth(year);
    }

    @GetMapping("/bill-sales")
    public List<BillSaleEntity> getBillSale() {
        return reportService.getBillSale();
    }

    @GetMapping("/bill-sales-detail/{id}")
    public List<BillSaleDetailEntity> getBillSaleDetailById(@PathVariable Long id) {
        return reportService.getBillSaleDetailById(id);
    }

    @DeleteMapping("/bill-sales/{id}")
    public void cancelBillSaleDetail(@PathVariable Long id) {
        reportService.cancelBillSale(id);
    }

    @PutMapping("/bill-sales/{id}")
    public void updateBillSaleDetail(@PathVariable Long id) {
        reportService.updateBillSale(id);
    }
}
