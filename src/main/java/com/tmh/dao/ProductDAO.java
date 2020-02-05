package com.tmh.dao;

import java.util.List;

import com.tmh.entities.Product;

public interface ProductDAO extends BaseDAO<Integer, Product> {
	
	List<Product> findByCategoryId(int categoryId);

	List<Product> findByKeyword(String keyword);
	
	List<Product> findAll();
	
	List<Product> findNotDeletedProducts();
}
