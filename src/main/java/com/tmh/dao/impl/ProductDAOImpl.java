package com.tmh.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.hibernate.SessionFactory;

import com.tmh.dao.GenericDAO;
import com.tmh.dao.ProductDAO;
import com.tmh.entities.Category;
import com.tmh.entities.Product;

public class ProductDAOImpl extends GenericDAO<Integer, Product> implements ProductDAO {

	public ProductDAOImpl() {
		super(Product.class);
	}

	public ProductDAOImpl(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> findAll() {
		return getSession().createQuery("from Product").getResultList();
	}
	@Override
	public List<Product> findByKeyword(String keyword) {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Product> query = builder.createQuery(Product.class);
		Root<Product> root = query.from(Product.class);
		Join<Product, Category> category = root.join("category");
		
		query.select(root);

		query.where(builder.or(
				builder.like(root.get("name"), "%" + keyword + "%"),
				builder.like(root.get("description"), "%" + keyword + "%"),
				builder.like(category.get("name"), "%" + keyword + "%"))
				);

		return getSession().createQuery(query).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Product> findNotDeletedProducts() {
		return getSession().createQuery("from Product where deleted = 0").getResultList();
	}
	
	@Override
	public List<Product> findByCategoryId(int categoryId) {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Product> query = builder.createQuery(Product.class);
		Root<Product> root = query.from(Product.class);
		Join<Product, Category> category = root.join("category");
		
		query.select(root);
		
		query.where(builder.equal(category.get("id"), categoryId));
		
		return getSession().createQuery(query).getResultList();
	}

}
