package com.group4.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.group4.DTO.CategoryDTO;
import com.group4.DTO.DiscountDTO;
import com.group4.DTO.PostDTO;
import com.group4.DTO.TemplateDTO;
import com.group4.service.CategoryService;
import com.group4.service.DiscountService;
import com.group4.service.PostService;
import com.group4.service.TemplateModelAttributeService;
import com.group4.service.TemplateService;

@Controller
public class ShowPostController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private PostService postService;

	@Autowired
	private DiscountService discountService;
	
	@Autowired
	private TemplateService templateService;
	
	@Autowired
	private TemplateModelAttributeService templateModelAttributeService;
	
	private Model addModelAttributeToTemplate(String templateLink, Model model, PostDTO post, CategoryDTO category, String pageString) {
		switch (templateLink) {
		case "postTemplate":{
			model = templateModelAttributeService.getPostTemplateModel(model, post, category);
			break;
		}
		
		case "course-template":{
			model = templateModelAttributeService.getCourseTemplateModel(model, post, category);
			break;
		}
		
		case "category1":{
			model = templateModelAttributeService.getCategory1Model(model, post);
			break;
		}
		
		case "category2":{
			model = templateModelAttributeService.getCategory2Model(model, post, pageString);
			break;
		}
		
		case "category3":{	
			model = templateModelAttributeService.getCategory3Model(model, post, category);
			break;
		}
		
		case "course-category1":{
			model = templateModelAttributeService.getCourseCategory1Model(model, post, pageString);
			break;
		}
		
		case "course-category2":{
			model = templateModelAttributeService.getCourseCategory2Model(model, post, pageString);
			break;
		}
		
		case "discountTemplate":{		
			model = templateModelAttributeService.getDiscountTemplateModel(model, post);
			break;
		}
		
		default:
			return null;
		}
		return model;
	}
	
	
	
	@RequestMapping("/{link}")
	public String viewProductDetailPage(@PathVariable(required = true,value = "link") String link,
			@RequestParam(name = "page", required = false, defaultValue = "1") String pageString,
			Model model) {
		PostDTO postDTO = null;
		
		CategoryDTO categoryDTO = categoryService.findByPath(link);
		if(categoryDTO != null) {
			Integer postID = categoryDTO.getPostId();
			postDTO = postService.findByPostIDAndStatus(postID, true);
		}
		else{
			try {
				postDTO = postService.findByPostIDAndStatus(Integer.parseInt(link), true);
			} catch(NumberFormatException ex) {
				return "redirect:/";
			}
		}
		
		if(postDTO == null) {
			return "redirect:/";
		}
		model.addAttribute("Post", postDTO);
		
		TemplateDTO templateDTO = templateService.findByPostId(postDTO.getPostId());
		String templateLink = templateDTO.getTemplateLink();
		CategoryDTO category = categoryService.findByCategoryId(postDTO.getCategoryId());
		
		model = addModelAttributeToTemplate(templateLink, model, postDTO, category, pageString);
		if(model == null) {
			return "redirect:/";
		}
		
		CategoryDTO categoryDTOTmp = category;
		if(categoryDTOTmp != null) {
			List<CategoryDTO> preCategoryList = new ArrayList<>();
			preCategoryList.add(categoryDTOTmp);
			while(categoryDTOTmp.getPreCategoryId() != null) {
				categoryDTOTmp = categoryService.findByCategoryId(categoryDTOTmp.getPreCategoryId());
				preCategoryList.add(categoryDTOTmp);
			}
			List<CategoryDTO> breadCrumbCategoryList = new ArrayList<>();
			for(int i=preCategoryList.size()-1 ; i >= 0 ;i--) {
				breadCrumbCategoryList.add(preCategoryList.get(i));
			}
			model.addAttribute("breadCrumbCategoryList", breadCrumbCategoryList);
		}
		
		List<DiscountDTO> couponList = discountService.findByStatus(true);
		model.addAttribute("couponList", couponList);
		
		return templateLink;
	}
}
