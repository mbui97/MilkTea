package com.tmh.controller;

import java.time.LocalDateTime;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tmh.bean.Cart;
import com.tmh.bean.CartItem;
import com.tmh.entities.Order;
import com.tmh.entities.OrderItem;
import com.tmh.entities.User;
import com.tmh.service.OrderItemService;
import com.tmh.service.OrderService;
import com.tmh.service.ProductService;
import com.tmh.service.impl.UserAuth;

@Controller
@SessionAttributes("cart")
public class CartController {

	private static final Logger logger = Logger.getLogger(CartController.class);

	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderItemService orderItemService;
	
	@RequestMapping(value = "/products/{id}")
	public String showProductDetail(@PathVariable("id") int id, Model model) {
		logger.info("show product detail");
	  
	 	model.addAttribute("product", productService.findById(id));
	  
	 	return "views/client/productDetail";
	}
	
	@RequestMapping(value = "/cart")
	public String showCart(Model model, @ModelAttribute("cart") Cart cart) {
		logger.info("show cart");

		if (cart != null) {
			model.addAttribute("cart", cart);
		} else {
			model.addAttribute("cart", new Cart());
		}

		return "views/client/cart";
	}

	@RequestMapping(value = "/add-to-cart", method = RequestMethod.POST)
	public String addToCart(@ModelAttribute("cart") Cart cart, @RequestParam("productId") int productId,
			@RequestParam("quantity") int quantity, RedirectAttributes redirectAttributes) {
		logger.info("add to cart");

		if (cart == null) {
			cart = new Cart();
		}

		cart.addProduct(productService.findById(productId), quantity);
		redirectAttributes.addFlashAttribute("cart", cart);

		return "redirect:/cart";
	}

	@RequestMapping(value = "/update-cart", method = RequestMethod.POST)
	public String updateCart(@ModelAttribute("cart") Cart cart, RedirectAttributes redirectAttributes) {
		logger.info("update cart");

		redirectAttributes.addFlashAttribute("cart", cart);

		return "redirect:/cart";
	}
	
	@RequestMapping(value = "/cart/{id}/remove")
	public String deleteCart(@ModelAttribute("cart") Cart cart, @PathVariable("id") int id, RedirectAttributes redirectAttributes) {
		logger.info("remove products in cart");
		
		cart.removeProduct(id);

		redirectAttributes.addFlashAttribute("cart", cart);

		return "redirect:/cart";
	}
	
	@RequestMapping(value = "/create-order")
	public String submitOrder(@ModelAttribute("cart") Cart cart, Model model) {
		logger.info("create order");
		
		Order order = new Order();
		
		model.addAttribute("cart", cart);
		model.addAttribute("user", getCurrentUser());
		model.addAttribute("order", order);
		
		return "views/client/order";
	}
	
	@RequestMapping(value = "/submit-checkout", method = RequestMethod.POST)
	public String submitCheckout(@ModelAttribute("order") Order order, BindingResult bindingResult, @ModelAttribute("cart") Cart cart, RedirectAttributes redirectAttributes) {
		logger.info("submit checkout");
		
		order.setUser(getCurrentUser());
		order.setTotal(cart.getAmountTotal());
		order.setStatus(0);
		order.setIsDeleted(0);
		order.setCreateOrderDate(LocalDateTime.now());
		
		orderService.saveOrUpdate(order);
		
		Order mOrder = orderService.findNotDeletedOrders().get(orderService.findNotDeletedOrders().size() - 1);
		
		for (CartItem cartItem : cart.getCartItems()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setOrder(mOrder);
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setIsDeleted(0);
			
			orderItemService.saveOrUpdate(orderItem);
		}
		
		redirectAttributes.addFlashAttribute("order", mOrder);
		redirectAttributes.addFlashAttribute("cart", cart);
		
		return "redirect:/confirm-order";
	}
	
	@RequestMapping(value = "/confirm-order")
	public String confirmOrder(@ModelAttribute("order") Order order, @ModelAttribute("cart") Cart cart, Model model) {
		logger.info("confirm order");
		
		model.addAttribute("order", order);
		model.addAttribute("cart", cart);
		
		return "views/client/checkout";
	}
	
	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserAuth userAuth = (UserAuth) authentication.getPrincipal();
		
		return userAuth.getUser();
	}
	
	@ModelAttribute("cart")
	public Cart cart() {
	    return new Cart();
	}
	
}
