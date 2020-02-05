package com.tmh.entities;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
public class Category {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
	private List<Product> products;
	
	@Column(name = "name")
	@NotEmpty(message = "{name.not.empty}")
	private String name;
	
	@Column(name = "description")
	@NotEmpty(message = "{description.not.empty}")
	private String description;
	
	@Column(name = "image")
	@NotEmpty(message = "{image.not.empty}")
	private String image;
	
	@Column(name = "deleted")
	private Integer isDeleted;
	
	@Column(name = "created_at")
	@CreationTimestamp
	private LocalDateTime createDateTime;
	
	@Column(name = "updated_at")
	@UpdateTimestamp
	private LocalDateTime updateDateTime;
	
	public String getDeletedString() {
		if (this.isDeleted == 0) {
			return "NOT DELETED";
		}
		
		else {
			return "DELETED";
		}
	}
}
