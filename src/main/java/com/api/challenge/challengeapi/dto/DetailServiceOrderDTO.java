package com.api.challenge.challengeapi.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.api.challenge.challengeapi.model.OrderStatus;
import com.api.challenge.challengeapi.model.ServiceOrder;

public class DetailServiceOrderDTO {

    private Long id;
    private LocalDateTime createdDate;
    private String customerName;
    private String responsibleName;
    private String productModel;
    private String productType;
    private String issue;
    private OrderStatus orderStatus;
    private List<OrderFollowUpDTO> orderFollowUps;

    public DetailServiceOrderDTO(ServiceOrder serviceOrder) {
        this.id = serviceOrder.getId();
        this.createdDate = serviceOrder.getCreatedDate();
        this.customerName = serviceOrder.getCustomer().getName();
        this.responsibleName = serviceOrder.getResponsible().getName();
        this.productModel = serviceOrder.getProductModel();
        this.productType = serviceOrder.getProductType();
        this.issue = serviceOrder.getIssue();
        this.orderStatus = serviceOrder.getOrderStatus();
        this.orderFollowUps = new ArrayList<>();
        this.orderFollowUps.addAll(serviceOrder.getOrderFollowUps().stream().map(OrderFollowUpDTO::new).collect(Collectors.toList()));
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    
    public String getCustomerName() {
        return customerName;
    }

    public String getResponsibleName() {
        return responsibleName;
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

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<OrderFollowUpDTO> getOrderFollowUps() {
        return orderFollowUps;
    }

    public static List<DetailServiceOrderDTO> convert(List<ServiceOrder> serviceOrder) {
        return serviceOrder.stream().map(DetailServiceOrderDTO::new).collect(Collectors.toList());
    }

}
