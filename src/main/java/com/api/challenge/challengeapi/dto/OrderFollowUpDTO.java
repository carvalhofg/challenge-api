package com.api.challenge.challengeapi.dto;

import java.time.LocalDateTime;

import com.api.challenge.challengeapi.model.OrderFollowUp;

public class OrderFollowUpDTO {
    
    private Long id;
    private String description;
    private LocalDateTime followUpDate;
    
    public OrderFollowUpDTO(OrderFollowUp orderFollowUp) {
        this.id = orderFollowUp.getId();
        this.description = orderFollowUp.getDescription();
        this.followUpDate = orderFollowUp.getFollowUpDate();
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getFollowUpDate() {
        return followUpDate;
    }

}
