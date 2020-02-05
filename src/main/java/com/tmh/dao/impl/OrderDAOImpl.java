package com.tmh.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.hibernate.SessionFactory;

import com.tmh.dao.GenericDAO;
import com.tmh.dao.OrderDAO;
import com.tmh.entities.Order;
import com.tmh.entities.User;

public class OrderDAOImpl extends GenericDAO<Integer, Order> implements OrderDAO {

	public OrderDAOImpl() {
		super(Order.class);
	}
	
	public OrderDAOImpl(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Order> findAll() {
		return getSession().createQuery("from Order").getResultList();
	}
	
	@Override
	public List<Order> findByKeyword(String keyword) {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Order> query = builder.createQuery(Order.class);
		Root<Order> root = query.from(Order.class);
		Join<Order, User> user = root.join("user");
		query.select(root);
		
		query.where(builder.or(
				builder.like(root.get("customerName"), "%" + keyword + "%"),
				builder.like(root.get("customerPhone"), "%" + keyword + "%"),
				builder.like(root.get("customerAddress"), "%" + keyword + "%"),
				builder.like(root.get("note"), "%" + keyword + "%"),
				builder.like(user.get("email"), "%" + keyword + "%"),
				builder.like(user.get("fullName"), "%" + keyword + "%")));
		
		return getSession().createQuery(query).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Order> findNotDeletedOrders() {
		return getSession().createQuery("from Order where deleted = 0").getResultList();
	}
	
}
