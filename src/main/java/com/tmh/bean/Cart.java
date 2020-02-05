package com.tmh.bean;

import java.util.ArrayList;
import java.util.List;

import com.tmh.entities.Product;
import com.tmh.entities.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Cart {
	
	private int id;
	
	private User user;
	
	private List<CartItem> cartItems = new ArrayList<CartItem>();
	
	public CartItem findCartItemByProductId(int productId) {
		for (CartItem cartItem : this.cartItems) {
			if (cartItem.getProduct().getId() == productId) {
				return cartItem;
			}
		}
		return null;
	}
	
	public void addProduct(Product product, int quantity) {
		CartItem cartItem = this.findCartItemByProductId(product.getId());
		
		if (cartItem == null) {
			cartItem = new CartItem();
			cartItem.setQuantity(quantity);
			cartItem.setProduct(product);
			this.cartItems.add(cartItem);
		}
		else {
			int newQuantity = cartItem.getQuantity() + quantity;
			
			if (newQuantity <= 0) {
				this.cartItems.remove(cartItem);
			} else {
				cartItem.setQuantity(newQuantity);
			}
		}
	}
	
	public void updateProduct(int productId, int quantity) {
		CartItem cartItem = this.findCartItemByProductId(productId);
		
		if (cartItem != null) {
			if (quantity <= 0) {
				this.cartItems.remove(cartItem);
			} else {
				cartItem.setQuantity(quantity);
			}
		}
	}
	
	public void removeProduct(Product product) {
		CartItem cartItem = this.findCartItemByProductId(product.getId());
		
		if (cartItem != null) {
			this.cartItems.remove(cartItem);
		}
	}
	
	public void removeProduct(int productId) {
		CartItem cartItem = this.findCartItemByProductId(productId);
		
		if (cartItem != null) {
			this.cartItems.remove(cartItem);
		}
	}
	
	public boolean isEmpty() {
        return this.cartItems.isEmpty();
    }
	
	public int getQuantityTotal() {
		int quantity = 0;
		for (CartItem cartItem : this.cartItems) {
			quantity += cartItem.getQuantity();
		}
		return quantity;
	}
	
	public float getAmountTotal() {
		float total = 0;
		for (CartItem cartItem : this.cartItems) {
			total += cartItem.getAmount();
		}
		return total;
	}
	
	public void updateQuantity(Cart cart) {
		if (cart != null) {
			List<CartItem> cartItems = cart.getCartItems();
			for (CartItem cartItem : cartItems) {
				this.updateProduct(cartItem.getProduct().getId(), cartItem.getQuantity());
			}
		}
	}
}
