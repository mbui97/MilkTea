package com.tmh.service.impl;

import com.tmh.dao.CategoryDAO;
import com.tmh.dao.OrderDAO;
import com.tmh.dao.OrderItemDAO;
import com.tmh.dao.ProductDAO;
import com.tmh.dao.UserDAO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BaseServiceImpl {

	protected ProductDAO productDAO;
	
	protected UserDAO userDAO;
	
	protected CategoryDAO categoryDAO;
	
	protected OrderDAO orderDAO;
	
	protected OrderItemDAO orderItemDAO;
	
}
