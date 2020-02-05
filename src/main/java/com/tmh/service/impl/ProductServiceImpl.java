package com.tmh.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;

import com.tmh.entities.Product;
import com.tmh.service.ProductService;

public class ProductServiceImpl extends BaseServiceImpl implements ProductService {

	private static final Logger logger = Logger.getLogger(ProductServiceImpl.class);
	
	@Override
	public Product saveOrUpdate(Product entity) {
		try {
			return getProductDAO().saveOrUpdate(entity);
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}
	
	@Override
	public Product findById(Serializable key) {
		try {
			return getProductDAO().findById(key);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	@Override
	public boolean delete(Product entity) {
		try {
			getProductDAO().delete(entity);
			return true;
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}
	
	@Override
	public List<Product> findAll() {
		try {
			return getProductDAO().findAll();
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	@Override
	public List<Product> findByKeyword(String keyword) {
		try {
			return getProductDAO().findByKeyword(keyword);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	@Override
	public List<Product> findNotDeletedProducts() {
		try {
			return getProductDAO().findNotDeletedProducts();
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	@Override
	public List<Product> findByCategoryId(int categoryId) {
		try {
			return getProductDAO().findByCategoryId(categoryId);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	@Override
	public boolean deleteProduct(Product product) {
		try {
			product.setIsDeleted(1);
			getProductDAO().saveOrUpdate(product);
			return true;
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}
	
}
