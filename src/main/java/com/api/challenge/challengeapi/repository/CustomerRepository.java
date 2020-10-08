package com.api.challenge.challengeapi.repository;

import com.api.challenge.challengeapi.model.Customer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
    
    Customer findByName(String customerName);
}
