package com.tmh.service;

import java.util.List;

import com.tmh.entities.Product;


public interface ProductService extends BaseService<Integer, Product> {
	
	List<Product> findByCategoryId(int categoryId);
	
	List<Product> findByKeyword(String keyword);

    List<Product> findAll();
	
    List<Product> findNotDeletedProducts();
    
    boolean deleteProduct(Product product);
	
}
