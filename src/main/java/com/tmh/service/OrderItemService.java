package com.tmh.service;

import java.util.List;

import com.tmh.entities.OrderItem;

public interface OrderItemService extends BaseService<Integer, OrderItem> {
	
	List<OrderItem> findByKeyword(String keyword);
	
	List<OrderItem> findAll();
	
	List<OrderItem> findByOrderId(int orderId);
	
	boolean deleteOrderItem(OrderItem orderItem);
	
}