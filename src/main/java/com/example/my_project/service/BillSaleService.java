package com.example.my_project.service;

import com.example.my_project.entity.BillSaleDetailEntity;
import com.example.my_project.entity.BillSaleEntity;
import com.example.my_project.entity.SaleTempEntity;
import com.example.my_project.entity.UserEntity;
import com.example.my_project.jpa.EndSale;
import com.example.my_project.repository.BillSaleDetailRepository;
import com.example.my_project.repository.BillSaleRepository;
import com.example.my_project.repository.SaleTempRepository;
import com.example.my_project.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BillSaleService {

    private final BillSaleRepository billSaleRepository;
    private final UserRepository userRepository;
    private final SaleTempRepository saleTempRepository;
    private final BillSaleDetailRepository billSaleDetailRepository;

    public BillSaleService(BillSaleRepository billSaleRepository,
                           UserRepository userRepository,
                           SaleTempRepository saleTempRepository,
                           BillSaleDetailRepository billSaleDetailRepository) {
        this.billSaleRepository = billSaleRepository;
        this.userRepository = userRepository;
        this.saleTempRepository = saleTempRepository;
        this.billSaleDetailRepository = billSaleDetailRepository;
    }

    public void endSale(String token, EndSale endSale) {
        UserService user = new UserService();
        Long userId = user.getUserIdFromToken(token);
        if (userId == null) {
            throw new RuntimeException("User not found.");
        }

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        BillSaleEntity billSale = new BillSaleEntity();
        billSale.setReceiveAmount(endSale.getReceiveAmount());
        billSale.setDiscount(endSale.getDiscount());
        billSale.setTotal(endSale.getTotal());
        billSale.setStatus("paid");
        billSale.setCreatedAt(LocalDate.now());
        billSale.setUser(userEntity);
        billSaleRepository.save(billSale);

        List<SaleTempEntity> saleTempEntities = saleTempRepository.findAllByUserIdOrderByIdAsc(userId);
        for (SaleTempEntity saleTemp : saleTempEntities) {
            BillSaleDetailEntity billSaleDetail = new BillSaleDetailEntity();
            billSaleDetail.setBillSale(billSale);
            billSaleDetail.setQuantity(saleTemp.getQuantity());
            billSaleDetail.setPrice(saleTemp.getPrice());
            billSaleDetail.setProduction(saleTemp.getProduction());
            billSaleDetailRepository.save(billSaleDetail);
        }
        saleTempRepository.deleteAllByUserId(userId);
    }
}
