package com.tmh.service;

import java.util.List;

import com.tmh.entities.Category;

public interface CategoryService extends BaseService<Integer, Category> {
	
	List<Category> findAll();
	
	List<Category> findByKeyword(String keyword);
	
	List<Category> findNotDeletedCategories();
	
	boolean deleteCategory(Category category);

}