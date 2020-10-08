package com.api.challenge.challengeapi.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.api.challenge.challengeapi.model.Customer;
import com.api.challenge.challengeapi.model.Responsible;
import com.api.challenge.challengeapi.model.ServiceOrder;
import com.api.challenge.challengeapi.repository.CustomerRepository;
import com.api.challenge.challengeapi.repository.ResponsibleRepository;

public class ServiceOrderForm {

    
    private ServiceOrder serviceOrder;

    @NotNull @NotEmpty
    private String customerName;

    @NotNull @NotEmpty
    private String responsibleName;

    @NotNull @NotEmpty
    private String productModel;

    private String productType;

    @NotNull @NotEmpty
    private String issue;

    public ServiceOrder getServiceOrder() {
        return serviceOrder;
    }

    public void setServiceOrder(ServiceOrder serviceOrder) {
        this.serviceOrder = serviceOrder;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getResponsibleName() {
        return responsibleName;
    }

    public void setResponsibleName(String responsibleName) {
        this.responsibleName = responsibleName;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public ServiceOrder convert(CustomerRepository customerRepository, ResponsibleRepository responsibleRepository) {
        Customer customer = customerRepository.findByName(customerName);
        Responsible responsible = responsibleRepository.findByName(responsibleName);

        return new ServiceOrder(customer, responsible, productModel, productType, issue);
    }
}
