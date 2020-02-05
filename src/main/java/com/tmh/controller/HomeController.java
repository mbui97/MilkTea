package com.tmh.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tmh.service.CategoryService;

@Controller
public class HomeController {

	private static final Logger logger = Logger.getLogger(HomeController.class);
	
	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value = "/")
	public String index(Model model) {
		logger.info("home page");
		
		model.addAttribute("categories", categoryService.findNotDeletedCategories());
		
		return "views/client/home";
	}
	
}
