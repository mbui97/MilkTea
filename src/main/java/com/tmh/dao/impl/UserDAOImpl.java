package com.tmh.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.SessionFactory;

import com.tmh.dao.GenericDAO;
import com.tmh.dao.UserDAO;
import com.tmh.entities.User;

public class UserDAOImpl extends GenericDAO<Integer, User> implements UserDAO {
	
	public UserDAOImpl() {
		super(User.class);
	}
	
	public UserDAOImpl(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> findAll() {
		return getSession().createQuery("from User").getResultList();
	}

	@Override
	public User findByEmail(String email) {
		logger.info("email: " + email);
		User user = (User) getSession().createQuery("from User where email = :email").setParameter("email", email).getSingleResult();
		return user;
		
	}
	
	@Override
	public List<User> findByKeyword(String keyword) {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<User> query = builder.createQuery(User.class);
		Root<User> root = query.from(User.class);
		query.select(root);
		
		query.where(builder.or(builder.like(root.get("email"), "%" + keyword + "%"),
				builder.like(root.get("fullName"), "%" + keyword + "%"),
				builder.like(root.get("phone"), "%" + keyword + "%"),
				builder.like(root.get("address"), "%" + keyword + "%")));
		
		return getSession().createQuery(query).getResultList();
	}
	
}
