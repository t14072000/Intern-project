package com.group4.serviceImplement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group4.DTO.CategoryDTO;
import com.group4.entity.Category;
import com.group4.repo.CategoryRepository;
import com.group4.service.CategoryService;

@Service
public class CategoryServiceImplement implements CategoryService{

	@Autowired
	private CategoryRepository catRepo;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public CategoryDTO findByCategoryName(String categoryName) {
		return catRepo.findByCategoryName(categoryName);
	}

	@Override
	public List<Integer> findAllSubCatId(int preCategoryId) {
		return catRepo.findAllSubCatId(preCategoryId);
	}

	@Override
	public List<Integer> findCategoryIDByCategoryNameContaining(String categoryName) {
		List <CategoryDTO> categoryList = catRepo.findByCategoryNameContaining(categoryName);
		List<Integer> categoryIDList = new ArrayList<Integer>();
		for (Iterator<CategoryDTO> iterator = categoryList.iterator(); iterator.hasNext();) {
			CategoryDTO category = (CategoryDTO) iterator.next();
			categoryIDList.add(category.getCategoryId());
		}
		return categoryIDList;
	}

	@Override
	public List<CategoryDTO> findAll() {
		List <Category> categoryList = catRepo.findAll();
		List<CategoryDTO> categoryDTOList = new ArrayList<CategoryDTO>();
		for (Iterator<Category> iterator = categoryList.iterator(); iterator.hasNext();) {
			Category category = (Category) iterator.next();
			CategoryDTO categoryDTO = new CategoryDTO(category.getCategoryId(), category.getCategoryName(), 
					category.getPreCategoryId(), category.getPath(), category.getPostId(), category.isStatus());
			categoryDTOList.add(categoryDTO);
		}
		return categoryDTOList;
	}

	@Override
	public List<CategoryDTO> findByStatus(boolean status) {
		return catRepo.findByStatus(status);
	}

	@Override
	public CategoryDTO findByPostId(Integer postID) {
		return catRepo.findByPostId(postID);
	}

	@Override
	public Integer getTotalCategories() {
		return catRepo.findAll().size();
	}

	@Override
	public Map<Integer, String> getCategoryIdAndNameMapper() {
		List <Category> categoryList = catRepo.findAll();
		Map<Integer,String> categoryMapper = new HashMap<Integer,String>();
		for (Iterator<Category> iterator = categoryList.iterator(); iterator.hasNext();) {
			Category category = (Category) iterator.next();
			categoryMapper.put(category.getCategoryId(), category.getCategoryName());
		}
		return categoryMapper;
	}

	@Override
	public int getMaxCategoryID() {
		// TODO Auto-generated method stub
		return catRepo.getMaxCategoryID();
	}

	@Override
	public boolean createUpdateCategory(CategoryDTO categoryDTO) {
		// TODO Auto-generated method stub
		try {
			Category category = mapper.map(categoryDTO, Category.class);
			catRepo.save(category);
			return true;
		}
		catch(IllegalArgumentException ex) {
			return false;
		}
	}

	@Override
	public CategoryDTO findByPath(String path) {
		// TODO Auto-generated method stub
		return catRepo.findByPath(path);
	}

	@Override
	public CategoryDTO findByCategoryId(Integer categoryId) {
		return catRepo.findByCategoryId(categoryId);
	}

	@Override
	public List<CategoryDTO> findByPreCategoryId(int preCategoryId) {
		// TODO Auto-generated method stub
		return catRepo.findByPreCategoryId(preCategoryId);
	}
}
