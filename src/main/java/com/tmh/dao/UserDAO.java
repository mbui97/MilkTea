package com.tmh.dao;

import java.util.List;

import com.tmh.entities.User;

public interface UserDAO extends BaseDAO<Integer, User> {
	
	List<User> findByKeyword(String keyword);
	
	List<User> findAll();
	
	User findByEmail(String email);
}
