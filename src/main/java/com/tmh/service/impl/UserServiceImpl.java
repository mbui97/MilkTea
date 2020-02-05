package com.tmh.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;

import com.tmh.entities.User;
import com.tmh.service.UserService;

public class UserServiceImpl extends BaseServiceImpl implements UserService {
	
private static final Logger logger = Logger.getLogger(ProductServiceImpl.class);
	
	@Override
	public User saveOrUpdate(User entity) {
		try {
			return getUserDAO().saveOrUpdate(entity);
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}
	
	@Override
	public User findById(Serializable key) {
		try {
			return getUserDAO().findById(key);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	@Override
	public boolean delete(User entity) {
		try {
			getUserDAO().delete(entity);
			return true;
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}
	
	@Override
	public List<User> findAll() {
		try {
			return getUserDAO().findAll();
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	@Override
	public User findByEmail(String email) {
		try {
			return getUserDAO().findByEmail(email);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	@Override
	public List<User> findByKeyword(String keyword) {
		try {
			return getUserDAO().findByKeyword(keyword);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
}
