package com.api.challenge.challengeapi.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class ServiceOrder {
    
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


	private LocalDateTime createdDate = LocalDateTime.now();
	@ManyToOne
	private Customer customer;
	@ManyToOne
	private Responsible responsible;
    private String productModel;
    private String productType;
	private String issue;
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus = OrderStatus.REGISTERED;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "serviceOrder")
    private List<OrderFollowUp> orderFollowUps = new ArrayList<>();

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServiceOrder other = (ServiceOrder) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public ServiceOrder() {
		/*Constructor*/ 
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
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

	public List<OrderFollowUp> getOrderFollowUps() {
		return orderFollowUps;
	}

	public void setOrderFollowUps(List<OrderFollowUp> orderFollowUps) {
		this.orderFollowUps = orderFollowUps;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Responsible getResponsible() {
		return responsible;
	}

	public void setResponsible(Responsible responsible) {
		this.responsible = responsible;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public ServiceOrder(Customer customer, Responsible responsible, String productModel, String productType, String issue) {
		this.customer = customer;
		this.responsible = responsible;
		this.productModel = productModel;
		this.productType = productType;
		this.issue = issue;
	}

	
}
