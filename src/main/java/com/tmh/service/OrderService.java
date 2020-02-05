package com.tmh.service;

import java.util.List;

import com.tmh.entities.Order;

public interface OrderService extends BaseService<Integer, Order> {
	
	List<Order> findByKeyword(String keyword);
	
	List<Order> findAll();
	
	List<Order> findNotDeletedOrders();
	
	boolean deleteOrder(Order order);
	
}
