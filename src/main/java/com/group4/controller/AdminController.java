package com.group4.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.group4.DTO.CategoryDTO;
import com.group4.DTO.PostDTO;
import com.group4.service.CategoryService;
import com.group4.service.CourseService;
import com.group4.service.DiscountService;
import com.group4.service.PostService;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	
	@Autowired
	private DiscountService discountService;
	
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private PostService postService;
	
	@Autowired
	private CourseService courseService;
	
	@RequestMapping(value = "/")
    public String admin() {
        return "redirect:/admin/dashboard";
    }
	
	@RequestMapping(value = "/dashboard")
	public String dashboardPage(Model model, Principal principal) {
		model.addAttribute("username", principal.getName());
		
		model.addAttribute("totalCourses", courseService.findAll().size());
		
		Integer totalDiscounts = discountService.getTotalDiscounts();
		model.addAttribute("totalDiscounts", totalDiscounts);
		
		Integer totalPosts = postService.getTotalPosts();
		model.addAttribute("totalPosts", totalPosts);
		
		Integer totalCategories = categoryService.getTotalCategories();
		model.addAttribute("totalCategories", totalCategories);
		
		List<PostDTO> postDTOList = postService.getAllPosts();
		model.addAttribute("postDTOList", postDTOList);
		
		Map<Integer,String> categoryMapper = categoryService.getCategoryIdAndNameMapper();
		model.addAttribute("categoryMapper", categoryMapper);
		
		List<CategoryDTO> categoryDTOList = categoryService.findAll();
		model.addAttribute("categoryDTOList", categoryDTOList);
		
		return "dashboard";
	}

	

}
