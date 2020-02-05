package com.tmh.controller.admin;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tmh.entities.Order;
import com.tmh.entities.OrderItem;

@Controller
public class OrderController extends AdminController {
	
	private static final Logger logger = Logger.getLogger(OrderController.class);

	@RequestMapping(value = "/orders")
	public String showOrderList(Model model) {
		logger.info("show orders list");
		
		model.addAttribute("orders", orderService.findNotDeletedOrders());
		
		return "views/admin/orderManager/orderList";
	}
	
	@RequestMapping(value = "/orders/{id}")
	public String showOrderDetail(@PathVariable("id") int id, Model model) {
		logger.info("show order detail");
		
		Order order = orderService.findById(id);
		
		if (order == null) {
			model.addAttribute("css", "error");
			model.addAttribute("msg", messageSource.getMessage("order.not.found", null, Locale.US));
		}
		
		// order.setTotal(totalAmountOfOrder(order));
		
		model.addAttribute("order", order);
		
		return "views/admin/orderManager/orderDetail";
	}
	
	@RequestMapping(value = "/orders/{id}/delete")
	public String deleteOrder(@PathVariable("id") int id, final RedirectAttributes redirectAttributes) {
		logger.info("delete order");
		
		Order order = orderService.findById(id);
		
		try {
			orderService.deleteOrder(order);
			
			List<OrderItem> orderItems = orderItemService.findByOrderId(id);
			for (OrderItem orderItem: orderItems) {
				orderItemService.deleteOrderItem(orderItem);
			}
			
			redirectAttributes.addFlashAttribute("css", "success");
			redirectAttributes.addFlashAttribute("msg", messageSource.getMessage("order.delete", null, Locale.US));
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("css", "error");
			redirectAttributes.addFlashAttribute("msg", messageSource.getMessage("order.delete.fail", null, Locale.US));
		}
		
		return "redirect:/admin/orders";
	}
	
	@RequestMapping(value = "/orders", method = RequestMethod.POST)
	public String submitAddOrUpdateOrder(@Valid @ModelAttribute("order") Order order, BindingResult bindingResult, Model model) {
		logger.info("submit add/update order");
		
		if (bindingResult.hasErrors()) {
            return "views/admin/orderManager/orderForm";
        }
		
		order.setUser(userService.findByEmail(order.getUser().getEmail()));
		
		try {
			orderService.saveOrUpdate(order);
		} catch (Exception e) {
			model.addAttribute("css", "error");
			model.addAttribute("msg", messageSource.getMessage("order.update.fail", null, Locale.US));
			
			return "views/admin/orderManager/orderForm";
		}
		
		model.addAttribute("order", orderService.findById(order.getId()));
		model.addAttribute("msg", messageSource.getMessage("order.update", null, Locale.US));
		
		return "views/admin/orderManager/orderDetail";
	}
	
	@RequestMapping(value = "/orders/{id}/edit")
	public String editOrder(@PathVariable("id") int id, Model model) {
		logger.info("edit order");
		
		Order order = orderService.findById(id);
		
		model.addAttribute("order", order);
		
		return "views/admin/orderManager/orderForm";
	}
	
	@RequestMapping(value = "/orders/search")
	public String searchOrder(@RequestParam("keyword") String keyword, Model model) {
		logger.info("search order");
		
		model.addAttribute("orders", orderService.findByKeyword(keyword));
		model.addAttribute("keyword", keyword);
		
		return "views/admin/orderManager/orderList";
	}
	
	private float totalAmountOfOrder(Order order) {
		
		float total = 0;
		
		for (OrderItem orderItem : order.getOrderItems()) {
			total += orderItem.getProduct().getUnitPrice() * orderItem.getQuantity();
		}
		
		return total;
	}
	
}
