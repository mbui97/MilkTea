package com.tmh.controller.admin;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tmh.entities.Product;

@Controller
public class ProductController extends AdminController {
	
	private static final Logger logger = Logger.getLogger(ProductController.class);
	
	@RequestMapping(value = "/products")
	public String showproductList(Model model) {
		logger.info("show products list");
		model.addAttribute("products", productService.findNotDeletedProducts());
		return "views/admin/productManager/productList";
	}

	@RequestMapping(value = "/products/{id}")
	public String showproductDetail(@PathVariable("id") int id, Model model) {
		logger.info("show product detail");

		Product product = productService.findById(id);

		if (product == null) {
			model.addAttribute("css", "error");
			model.addAttribute("msg", messageSource.getMessage("product.notfound", null, Locale.US));
		}
		
		model.addAttribute("categoryName", categoryService.findById(product.getCategory().getId()).getName());
		model.addAttribute("product", product);

		return "views/admin/productManager/productDetail";
	}

	@RequestMapping(value = "/products/{id}/delete")
	public String deleteProduct(@PathVariable("id") int id, final RedirectAttributes redirectAttributes) {
		logger.info("delete product");

		Product product = productService.findById(id);
		
		try {
			productService.deleteProduct(product);
			redirectAttributes.addFlashAttribute("css", "success");
			redirectAttributes.addFlashAttribute("msg", messageSource.getMessage("product.delete", null, Locale.US));
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("css", "error");
			redirectAttributes.addFlashAttribute("msg",
					messageSource.getMessage("product.delete.fail", null, Locale.US));
		}

		return "redirect:/admin/products";
	}

	@RequestMapping(value = "/products/add")
	public String addNewProduct(Model model) {
		logger.info("add product");

		model.addAttribute("productForm", new Product());
		model.addAttribute("status", "add");
		model.addAttribute("categories_menu", categoryService.findNotDeletedCategories());

		return "views/admin/productManager/productForm";
	}

	@RequestMapping(value = "/products", method = RequestMethod.POST)
	public String submitAddOrUpdateProduct(@Valid @ModelAttribute("productForm") Product product,
			BindingResult bindingResult, @RequestParam("status") String status,@RequestParam("imageUrl") MultipartFile file, HttpServletRequest request, Model model) {
		logger.info("submit add/update product");

		if (bindingResult.hasErrors()) {

			model.addAttribute("status", status);

			return "views/admin/productManager/productForm";
		}
		//String path = request.getSession().getServletContext().getRealPath("/assets/admin/img/products/"	);
		//String path = request.getRequestURI().toString() + "/assets/admin/img/products/";
		//String path = resourceLoader.getResource("assets/admin/img/products/").toString();
		String path = System.getProperty("user.home") +"/Master/Java/TKC_Projects/naitei-order/src/main/webapp/assets/admin/img/products/";
		logger.info(path);
		
        try {
             FileUtils.forceMkdir(new File(path));
             File upload = new File(path + file.getOriginalFilename());
             file.transferTo(upload);
             product.setImage(file.getOriginalFilename());
        } catch (IOException ex) {
             ex.printStackTrace();
        }
		try {
			// product.setCategory(categoryService.findById(categoryMenu.getId()));

			productService.saveOrUpdate(product);
		} catch (Exception e) {
			model.addAttribute("status", status);
			model.addAttribute("css", "error");
			if (status.equals("add")) {
				model.addAttribute("msg", messageSource.getMessage("product.add.fail", null, Locale.US));
			} else {
				model.addAttribute("msg", messageSource.getMessage("product.update.fail", null, Locale.US));
			}

			return "views/admin/productManager/productForm";
		}

		model.addAttribute("product", product);
		model.addAttribute("categoryName", categoryService.findById(product.getCategory().getId()).getName());

		if (status.equals("add")) {
			model.addAttribute("msg", messageSource.getMessage("product.add", null, Locale.US));
		} else {
			model.addAttribute("msg", messageSource.getMessage("product.update", null, Locale.US));
		}

		return "views/admin/productManager/productDetail";
	}

	@RequestMapping(value = "/products/{id}/edit")
	public String editProduct(@PathVariable("id") int id, Model model) {
		logger.info("edit product");

		Product product = productService.findById(id);

		model.addAttribute("productForm", product);
		model.addAttribute("status", "edit");
		model.addAttribute("categories_menu", categoryService.findNotDeletedCategories());

		return "views/admin/productManager/productForm";
	}

	@RequestMapping(value = "/products/search")
	public String searchProduct(@RequestParam("keyword") String keyword, @ModelAttribute("productList") Product product,Model model) {
		logger.info("search product");

		//model.addAttribute("categoryName", categoryService.findById(product.getCategory().getId()).getName());
		model.addAttribute("products", productService.findByKeyword(keyword));
		model.addAttribute("keyword", keyword);

		return "views/admin/productManager/productList";
	}


}
