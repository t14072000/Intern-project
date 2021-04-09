package com.group4.service;

import org.springframework.ui.Model;

import com.group4.DTO.CategoryDTO;
import com.group4.DTO.PostDTO;

public interface TemplateModelAttributeService {

	Model getPostTemplateModel(Model model, PostDTO post, CategoryDTO category);
	
	Model getCourseTemplateModel(Model model, PostDTO post, CategoryDTO category);
	
	Model getCategory1Model(Model model, PostDTO post);
	
	Model getCategory2Model(Model model, PostDTO post, String pageString);
	
	Model getCategory3Model(Model model, PostDTO post, CategoryDTO category);
	
	Model getCourseCategory1Model(Model model, PostDTO post, String pageString);
	
	Model getCourseCategory2Model(Model model, PostDTO post, String pageString);
	
	Model getDiscountTemplateModel(Model model, PostDTO post);
}
