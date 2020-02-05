package com.tmh.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tmh.entities.User;
import com.tmh.service.UserService;

@Controller
public class LoginController {
	@Autowired
	private UserService userService;

	
	private static final Logger logger = Logger.getLogger(HomeController.class);
	
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/login")
	public String login(Authentication auth,@RequestParam(value = "error", required = false) final String error,@RequestParam(value = "registerSucess", required = false) final String register, final Model model) {
		if(auth != null) return "redirect:/";
		logger.info("login page");
		if (error != null) {
			model.addAttribute("css", "error");
			model.addAttribute("msg", messageSource.getMessage("login.fail", null, Locale.US));
		}
		if (register != null) {
			//model.addAttribute("css", "error");
			model.addAttribute("msg", messageSource.getMessage("register.success", null, Locale.US));
		}
		return "views/admin/login";
	}
	
	@RequestMapping(value = "/logout")
    public String logout() {
		logger.info("logout progess");
        return "redirect:/login";
    }
	
	@RequestMapping(value = "/register")
	public String register(Model model) {
		logger.info("register page");
		model.addAttribute("userForm", new User());
		return "views/admin/register";
	}

	@RequestMapping(value = "/registerProgess", method = RequestMethod.POST)
	public String submitAddOrUpdateUser(@Valid @ModelAttribute("userForm") User user, BindingResult bindingResult, Model model) {
		logger.info("submit register user");

		if (bindingResult.hasErrors()) {
			return "views/admin/register";
		}
		user.setRole(0);
		logger.info(user.getRole());
		String hash = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
		user.setPassword(hash);

		try {

			userService.saveOrUpdate(user);
		} catch (Exception e) {

			model.addAttribute("css", "success");
			model.addAttribute("msg", messageSource.getMessage("register.fail", null, Locale.US));

			return "views/admin/register";
		}

		return "redirect:/login?registerSucess=true";
	}

	
}
