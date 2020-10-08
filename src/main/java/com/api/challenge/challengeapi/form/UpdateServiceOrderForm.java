package com.api.challenge.challengeapi.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.api.challenge.challengeapi.model.OrderStatus;
import com.api.challenge.challengeapi.model.ServiceOrder;
import com.api.challenge.challengeapi.repository.ServiceOrderRepository;

public class UpdateServiceOrderForm {
    
    private OrderStatus orderStatus;

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public ServiceOrder update(Long id, ServiceOrderRepository serviceOrderRepository) {
        ServiceOrder serviceOrder = serviceOrderRepository.getOne(id);
        serviceOrder.setOrderStatus(orderStatus);

        return serviceOrder;
    }

}
