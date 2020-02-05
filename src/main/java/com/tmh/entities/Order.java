package com.tmh.entities;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
	private List<OrderItem> orderItems;
	
	@Column(name = "date_order")
	@NotNull
	@CreationTimestamp
	private LocalDateTime createOrderDate;
	
	@Column(name = "total")
	private float total;
	
	@Column(name = "note")
	private String note;
	
	@Column(name = "status")
	private Integer status;
	
	@Column(name = "deleted")
	private Integer isDeleted;
	
	@Column(name = "customer_name")
	@NotEmpty
	private String customerName;
	
	@Column(name = "customer_phone")
	@NotEmpty
	private String customerPhone;
	
	@Column(name = "customer_address")
	@NotEmpty
	private String customerAddress;
	
	@Column(name = "created_at")
	@CreationTimestamp
	private LocalDateTime createDateTime;
	
	@Column(name = "updated_at")
	@UpdateTimestamp
	private LocalDateTime updateDateTime;
	
	public String getStatusString() {
		if (this.status == 0) {
			return "PENDING";
		}
		
		if (this.status == 1) {
			return "APPROVED";
		}
		
		if (this.status == 2) {
			return "SHIPPING";
		}
		
		else {
			return "DONE";
		}
	}

	public String getDeletedString() {
		if (this.isDeleted == 0) {
			return "NOT DELETED";
		}
		
		else {
			return "DELETED";
		}
	}
}
