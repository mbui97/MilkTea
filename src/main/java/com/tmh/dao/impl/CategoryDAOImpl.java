package com.tmh.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.SessionFactory;
import com.tmh.dao.GenericDAO;
import com.tmh.dao.CategoryDAO;
import com.tmh.entities.Category;



public class CategoryDAOImpl extends GenericDAO<Integer, Category> implements CategoryDAO {
	
	public CategoryDAOImpl() {
		super(Category.class);
	}
	
	public CategoryDAOImpl(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Category> findAll() {
		return getSession().createQuery("from Category").getResultList();
	}
	
	@Override
	public List<Category> findByKeyword(String keyword) {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Category> query = builder.createQuery(Category.class);
		Root<Category> root = query.from(Category.class);
		query.select(root);
		
		query.where(builder.or(builder.like(root.get("name"), "%" + keyword + "%"),
				builder.like(root.get("description"), "%" + keyword + "%"),
				builder.like(root.get("image"), "%" + keyword + "%")));
		
		return getSession().createQuery(query).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Category> findNotDeletedCategories() {
		return getSession().createQuery("from Category where deleted = 0").getResultList();
	}
	
}