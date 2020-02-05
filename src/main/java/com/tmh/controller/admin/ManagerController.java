package com.tmh.controller.admin;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ManagerController extends AdminController {
	
	private static final Logger logger = Logger.getLogger(ManagerController.class);

	@RequestMapping(value = "")
	public String admin(Model model) {
		logger.info("admin home page");
		return "views/admin/home";
	}
	
}
