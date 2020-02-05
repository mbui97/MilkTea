package com.tmh.bean;

import com.tmh.entities.Product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItem {

	private int id;
	
	private Product product;
	
	private int quantity;
	
	public CartItem() {
		this.quantity = 0;
	}
	
	public float getAmount() {
		return this.product.getUnitPrice() * this.quantity;
	}
	
}
