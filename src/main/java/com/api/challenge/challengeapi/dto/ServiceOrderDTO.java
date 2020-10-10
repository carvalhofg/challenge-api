package com.api.challenge.challengeapi.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.api.challenge.challengeapi.model.Customer;
import com.api.challenge.challengeapi.model.Responsible;
import com.api.challenge.challengeapi.model.ServiceOrder;

public class ServiceOrderDTO {

    private Long id;
    private LocalDateTime createdDate;
    private Customer customer;
    private Responsible responsible;
    private String productModel;
    private String productType;
    private String issue;

    public ServiceOrderDTO(ServiceOrder serviceOrder) {
        this.id = serviceOrder.getId();
        this.createdDate = serviceOrder.getCreatedDate();
        this.customer = serviceOrder.getCustomer();
        this.responsible = serviceOrder.getResponsible();
        this.productModel = serviceOrder.getProductModel();
        this.productType = serviceOrder.getProductType();
        this.issue = serviceOrder.getIssue();
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Responsible getResponsible() {
        return responsible;
    }

    public String getProductModel() {
        return productModel;
    }

    public String getProductType() {
        return productType;
    }

    public String getIssue() {
        return issue;
    }

    public static List<ServiceOrderDTO> convert(List<ServiceOrder> serviceOrder) {
        return serviceOrder.stream().map(ServiceOrderDTO::new).collect(Collectors.toList());
    }
}
