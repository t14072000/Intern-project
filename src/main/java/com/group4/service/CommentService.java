package com.group4.service;

import java.util.List;

import com.group4.DTO.CommentDTO;

public interface CommentService {

	int getMaxCommentID();
	
	boolean createComment(CommentDTO commentDTO);
	
	List<CommentDTO> findByPostId(Integer postId);
}
