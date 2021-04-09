package com.group4.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.group4.DTO.PostDTO;

public interface PostService {
	List<PostDTO> getTop5PostByCategoryIdAndStatus(List<Integer> categoryID, boolean status);

	List<PostDTO> getTop20PostByCategoryIdAndStatus(List<Integer> categoryID, boolean status);

	List<PostDTO> getTop12PostByCategoryIdAndStatus(List<Integer> categoryID, boolean status);
	
	int getMaxPostID();
	
	boolean createUpdatePost(PostDTO postDTO);
	
	PostDTO findByPostIDAndStatus(int postID, boolean status);
	
	PostDTO findByPostId(int postID);
	
	List<PostDTO> findByTitleContainingAndCategoryId(String title, int categoryID);
	
	List<PostDTO> getAllPosts();
	
	Integer getTotalPosts();
	
	List<PostDTO> findByCategoryId(int categoryID);
	
	Page<PostDTO> findByCategoryIdInOrderByPostIdDesc(List<Integer> listCategoryId, Pageable pageable);
}
