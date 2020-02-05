package com.tmh.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;

import com.tmh.entities.Category;
import com.tmh.entities.Product;
import com.tmh.service.CategoryService;

public class CategoryServiceImpl extends BaseServiceImpl implements CategoryService {

	private static final Logger logger = Logger.getLogger(CategoryServiceImpl.class);

	@Override
	public Category saveOrUpdate(Category entity) {
		try {
			return getCategoryDAO().saveOrUpdate(entity);
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}

	@Override
	public Category findById(Serializable key) {
		try {
			return getCategoryDAO().findById(key);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	@Override
	public boolean delete(Category entity) {
		try {
			getCategoryDAO().delete(entity);
			return true;
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}

	@Override
	public List<Category> findAll() {
		try {
			return getCategoryDAO().findAll();
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	@Override
	public List<Category> findByKeyword(String keyword) {
		try {
			return getCategoryDAO().findByKeyword(keyword);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Category> findNotDeletedCategories() {
		try {
			return getCategoryDAO().findNotDeletedCategories();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean deleteCategory(Category category) {
		try {

			List<Product> products = getProductDAO().findByCategoryId(category.getId());

			for (Product product : products) {
				product.setIsDeleted(1);
				getProductDAO().saveOrUpdate(product);
			}
			
			Category mCategory = findById(category.getId());
			mCategory.setIsDeleted(1);
			getCategoryDAO().saveOrUpdate(mCategory);

			return true;
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}

}
