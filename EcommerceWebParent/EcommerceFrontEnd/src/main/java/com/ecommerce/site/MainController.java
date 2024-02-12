package com.ecommerce.site;

import com.ecommerce.category.CategoryService;
import com.ecommerce.common.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class MainController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping("")
	public String viewHomePage(Model model) {
		List<Category> categoryList = categoryService.listNoChildrenCategories();
		model.addAttribute("listCategories", categoryList);

		return "index";
	}
	
}
