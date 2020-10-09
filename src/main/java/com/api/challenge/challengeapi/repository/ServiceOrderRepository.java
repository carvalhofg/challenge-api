package com.api.challenge.challengeapi.repository;

import java.util.List;

import com.api.challenge.challengeapi.model.ServiceOrder;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long> {
    
    List<ServiceOrder> findByResponsibleName(String responsibleName);
    List<ServiceOrder> findByCustomerName(String customerName);
}
