package com.tmh.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tmh.service.CategoryService;
import com.tmh.service.OrderItemService;
import com.tmh.service.OrderService;
import com.tmh.service.ProductService;
import com.tmh.service.UserService;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	
	@Autowired
	protected MessageSource messageSource;

	@Autowired
	protected CategoryService categoryService;

	@Autowired
	protected UserService userService;

	@Autowired
	protected ProductService productService;
	
	@Autowired
	protected OrderService orderService;
	
	@Autowired
	protected OrderItemService orderItemService;
	
}
