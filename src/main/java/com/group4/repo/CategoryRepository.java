package com.group4.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.group4.DTO.CategoryDTO;
import com.group4.entity.Category;

public interface CategoryRepository extends CrudRepository<Category, Integer>{
	CategoryDTO findByCategoryName(String categoryName);
	
	CategoryDTO findByCategoryId(Integer categoryId);
	
	CategoryDTO findByPostId(Integer postID);
	
	@Query(value = "With CategoryList AS( "
			+ "	select parent.category_id "
			+ "	from category as parent "
			+ "	where parent.pre_category_id = :preCategoryId "
			+ "	union all "
			+ "	SELECT child.category_id "
			+ "	from category as child "
			+ "	Inner join CategoryList as CL "
			+ "	on child.pre_category_id = CL.category_id "
			+ "	where child.pre_category_id is not null ) "
			+ " select c.* from CategoryList c ", nativeQuery = true)
	List<Integer> findAllSubCatId(int preCategoryId);
	
	//chi dung de load bai tap len main page
	List<CategoryDTO> findByCategoryNameContaining(String categoryName);
	
	List<Category> findAll();
	
	List<CategoryDTO> findByStatus(boolean status);
	
	@Query(value = "SELECT coalesce(max(c.category_id), 0) FROM category c", nativeQuery = true)
	int getMaxCategoryID();
	
	CategoryDTO findByPath(String path);
	
	List<CategoryDTO> findByPreCategoryId(int preCategoryId);
	
}
