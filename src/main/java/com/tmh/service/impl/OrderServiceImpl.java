package com.tmh.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;

import com.tmh.entities.Order;
import com.tmh.entities.OrderItem;
import com.tmh.service.OrderService;

public class OrderServiceImpl extends BaseServiceImpl implements OrderService {
	
	private static final Logger logger = Logger.getLogger(ProductServiceImpl.class);
	
	@Override
	public Order saveOrUpdate(Order entity) {
		try {
			return getOrderDAO().saveOrUpdate(entity);
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}
	
	@Override
	public Order findById(Serializable key) {
		try {
			return getOrderDAO().findById(key);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	@Override
	public boolean delete(Order entity) {
		try {
			getOrderDAO().delete(entity);
			return true;
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}
	
	@Override
	public List<Order> findAll() {
		try {
			return getOrderDAO().findAll();
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	@Override
	public List<Order> findByKeyword(String keyword) {
		try {
			return getOrderDAO().findByKeyword(keyword);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	@Override
	public List<Order> findNotDeletedOrders() {
		try {
			return getOrderDAO().findNotDeletedOrders();
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	@Override
	public boolean deleteOrder(Order order) {
		try {
			
			List<OrderItem> orderItems = getOrderItemDAO().findByOrderId(order.getId());
			
			for (OrderItem orderItem: orderItems) {
				orderItem.setIsDeleted(1);
				getOrderItemDAO().saveOrUpdate(orderItem);
			}
			
			Order mOrder = getOrderDAO().findById(order.getId());
			mOrder.setIsDeleted(1);
			getOrderDAO().saveOrUpdate(mOrder);
			
			return true;
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}
	
}
