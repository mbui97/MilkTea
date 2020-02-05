package com.tmh.dao;

import java.util.List;

import com.tmh.entities.Order;

public interface OrderDAO extends BaseDAO<Integer, Order> {
	
	List<Order> findByKeyword(String keyword);
	
	List<Order> findAll();
	
	List<Order> findNotDeletedOrders();
	
}
