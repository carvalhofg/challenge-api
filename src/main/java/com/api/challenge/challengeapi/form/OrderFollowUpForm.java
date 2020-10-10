package com.api.challenge.challengeapi.form;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.api.challenge.challengeapi.model.OrderFollowUp;
import com.api.challenge.challengeapi.model.ServiceOrder;

public class OrderFollowUpForm {
    @NotNull @NotEmpty
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public OrderFollowUp convert(ServiceOrder serviceOrder) {
        return new OrderFollowUp(description, serviceOrder);
    }

}
