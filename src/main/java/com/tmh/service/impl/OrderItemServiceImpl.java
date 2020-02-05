package com.tmh.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;

import com.tmh.entities.OrderItem;
import com.tmh.service.OrderItemService;

public class OrderItemServiceImpl extends BaseServiceImpl implements OrderItemService {
	
	private static final Logger logger = Logger.getLogger(ProductServiceImpl.class);
	
	@Override
	public OrderItem saveOrUpdate(OrderItem entity) {
		try {
			return getOrderItemDAO().saveOrUpdate(entity);
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}
	
	@Override
	public OrderItem findById(Serializable key) {
		try {
			return getOrderItemDAO().findById(key);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	@Override
	public boolean delete(OrderItem entity) {
		try {
			getOrderItemDAO().delete(entity);
			return true;
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}
	
	@Override
	public List<OrderItem> findAll() {
		try {
			return getOrderItemDAO().findAll();
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	@Override
	public List<OrderItem> findByKeyword(String keyword) {
		try {
			return getOrderItemDAO().findByKeyword(keyword);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	@Override
	public List<OrderItem> findByOrderId(int orderId) {
		try {
			return getOrderItemDAO().findByOrderId(orderId);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	@Override
	public boolean deleteOrderItem(OrderItem orderItem) {
		try {
			orderItem.setIsDeleted(1);
			getOrderItemDAO().saveOrUpdate(orderItem);
			return true;
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}
	
}
