package com.tmh.dao;

import java.util.List;

import com.tmh.entities.OrderItem;

public interface OrderItemDAO extends BaseDAO<Integer, OrderItem> {
	
	List<OrderItem> findByKeyword(String keyword);
	
	List<OrderItem> findAll();
	
	List<OrderItem> findByOrderId(int orderId);
	
}
