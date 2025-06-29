package com.example.my_project.service;

import com.example.my_project.dto.DashboardResponse;
import com.example.my_project.dto.IncomePerMonth;
import com.example.my_project.entity.BillSaleDetailEntity;
import com.example.my_project.entity.BillSaleEntity;
import com.example.my_project.repository.BillSaleDetailRepository;
import com.example.my_project.repository.BillSaleRepository;
import com.example.my_project.repository.ProductionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {

    @PersistenceContext
    private EntityManager entityManager;

    private final BillSaleRepository billSaleRepository;
    private final BillSaleDetailRepository billSaleDetailRepository;
    private final ProductionRepository productionRepository;

    public ReportService(BillSaleRepository billSaleRepository,
                         BillSaleDetailRepository billSaleDetailRepository,
                         ProductionRepository productionRepository) {
        this.billSaleRepository = billSaleRepository;
        this.billSaleDetailRepository = billSaleDetailRepository;
        this.productionRepository = productionRepository;
    }

    @SuppressWarnings("unchecked")
    public List<IncomePerMonth> sumIncomePerMonth(int year) {
        String stmt = """
                SELECT
                    EXTRACT(MONTH FROM bse.created_at) AS month,
                    SUM(bsd.quantity * bsd.price) AS income
                FROM bill_sale_detail_entity bsd
                LEFT JOIN bill_sale_entity bse
                ON bsd.bill_sale_id = bse.id
                WHERE bse.status = 'paid'
                AND EXTRACT(YEAR FROM bse.created_at) = :year
                GROUP BY EXTRACT(MONTH FROM bse.created_at)
                """;
        Query query = entityManager.createNativeQuery(stmt);
        query.setParameter("year", year);

        List<Object[]> results = query.getResultList();
        List<IncomePerMonth> response = new ArrayList<>();
        for (Object[] row : results) {
            int month = ((Number) row[0]).intValue();
            double income = ((Number) row[1]).doubleValue();
            response.add(new IncomePerMonth(month, income));
        }
        return response;
    }

    public List<BillSaleEntity> getBillSale() {
        return billSaleRepository.findAll();
    }

    public List<BillSaleDetailEntity> getBillSaleDetailById(Long id) {
        return billSaleDetailRepository.findAllByBillSaleId(id);
    }

    public void cancelBillSale(Long id) {
        BillSaleEntity billSale = billSaleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BillSaleDetail not found."));
        billSale.setStatus("canceled");
        billSaleRepository.save(billSale);
    }

    public void updateBillSale(Long id) {
        BillSaleEntity billSale = billSaleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BillSaleDetail not found."));
        billSale.setStatus("paid");
        billSaleRepository.save(billSale);
    }

    @SuppressWarnings("unchecked")
    public DashboardResponse showDashboard() {
        try {
            String stmt = """
                    SELECT COALESCE(SUM(production_log_entity.quantity), 0) AS sum_qty
                    FROM production_log_entity
                    """;
            Query query = entityManager.createNativeQuery(stmt);
            Object result = query.getSingleResult();
            int sumQty = Integer.parseInt(result+"");

            stmt = """
                    SELECT COALESCE(SUM(bsd.quantity * bsd.price), 0) AS sum_income
                    FROM bill_sale_detail_entity bsd
                    LEFT JOIN bill_sale_entity bse
                    ON bsd.bill_sale_id = bse.id
                    WHERE bse.status = 'paid'
                    """;
            query = entityManager.createNativeQuery(stmt);
            result = query.getSingleResult();
            double sumIncome = Double.parseDouble(result+"");

            Long totalProduct = productionRepository.count();

            stmt = """
                    SELECT SUM(production_lost_entity.quantity) AS sum_qty
                    FROM production_lost_entity
                    """;
            query = entityManager.createNativeQuery(stmt);
            result = query.getSingleResult();
            int sumLoss = Integer.parseInt(result+"");
            return new DashboardResponse(sumQty, sumIncome, totalProduct, sumLoss);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
