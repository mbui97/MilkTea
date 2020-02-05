package com.tmh.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.hibernate.SessionFactory;

import com.tmh.dao.GenericDAO;
import com.tmh.dao.OrderItemDAO;
import com.tmh.entities.Order;
import com.tmh.entities.OrderItem;

public class OrderItemDAOImpl extends GenericDAO<Integer, OrderItem> implements OrderItemDAO {

	public OrderItemDAOImpl() {
		super(OrderItem.class);
	}
	
	public OrderItemDAOImpl(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderItem> findAll() {
		return getSession().createQuery("from OrderItem").getResultList();
	}
	
	@Override
	public List<OrderItem> findByKeyword(String keyword) {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<OrderItem> query = builder.createQuery(OrderItem.class);
		Root<OrderItem> root = query.from(OrderItem.class);
		query.select(root);
		
		query.where(builder.or(builder.like(root.get("email"), "%" + keyword + "%"),
				builder.like(root.get("fullName"), "%" + keyword + "%"),
				builder.like(root.get("phone"), "%" + keyword + "%"),
				builder.like(root.get("address"), "%" + keyword + "%")));
		
		return getSession().createQuery(query).getResultList();
	}
	
	@Override
	public List<OrderItem> findByOrderId(int orderId) {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<OrderItem> query = builder.createQuery(OrderItem.class);
		Root<OrderItem> root = query.from(OrderItem.class);
		Join<OrderItem, Order> order = root.join("order");
		
		query.select(root);
		
		query.where(builder.equal(order.get("id"), orderId));
		
		return getSession().createQuery(query).getResultList();
	}
	
}
