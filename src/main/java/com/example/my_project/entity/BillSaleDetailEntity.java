package com.example.my_project.entity;

import jakarta.persistence.*;

@Entity
public class BillSaleDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bill_sale_id")
    private BillSaleEntity billSale;

    @ManyToOne
    @JoinColumn
    private ProductionEntity production;

    private Integer quantity;
    private Double price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BillSaleEntity getBillSale() {
        return billSale;
    }

    public void setBillSale(BillSaleEntity billSale) {
        this.billSale = billSale;
    }

    public ProductionEntity getProduction() {
        return production;
    }

    public void setProduction(ProductionEntity production) {
        this.production = production;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
