package com.group4.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.group4.DTO.PostDTO;
import com.group4.entity.Post;

public interface PostRepository extends CrudRepository<Post, Integer>{
	List<PostDTO> findTop5ByCategoryIdInAndStatusOrderByPostIdDesc(List<Integer> categoryID, boolean status);
	
	List<PostDTO> findTop20ByCategoryIdInAndStatusOrderByPostIdDesc(List<Integer> categoryID, boolean status);
	
	List<PostDTO> findTop12ByCategoryIdInAndStatusOrderByPostIdDesc(List<Integer> categoryID, boolean status);
	
	@Query(value = "SELECT coalesce(max(p.post_id), 0) FROM post p", nativeQuery = true)
	int getMaxPostID();
	
	PostDTO findByPostIdAndStatus(int postID, boolean status);
	
	PostDTO findByPostId(int postID);
	
	List<PostDTO> findByTitleContainingAndCategoryId(String title, int categoryID);
	
	List<Post> findAll();
	
	List<PostDTO> findByCategoryId(int categoryID);
	
	Page<PostDTO> findByCategoryIdInAndStatusTrueOrderByPostIdDesc(List<Integer> listCategoryId,Pageable pageable);
}
