package com.group4.service;

import java.util.List;
import java.util.Map;

import com.group4.DTO.CategoryDTO;

public interface CategoryService {
	CategoryDTO findByCategoryName(String categoryName);
	
	CategoryDTO findByPostId(Integer postID);
	
	List<Integer> findAllSubCatId(int preCategoryId);
	
	List<Integer> findCategoryIDByCategoryNameContaining(String categoryName);
	
	List<CategoryDTO> findAll();
	
	List<CategoryDTO> findByStatus(boolean status);
	
	Integer getTotalCategories();
	
	Map<Integer,String> getCategoryIdAndNameMapper();
	
	int getMaxCategoryID();
	
	boolean createUpdateCategory(CategoryDTO categoryDTO);
	
	CategoryDTO findByPath(String path);
	
	CategoryDTO findByCategoryId(Integer categoryId);
	
	List<CategoryDTO> findByPreCategoryId(int preCategoryId);
}
