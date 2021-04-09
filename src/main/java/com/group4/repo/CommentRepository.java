package com.group4.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.group4.DTO.CommentDTO;
import com.group4.entity.Comment;

public interface CommentRepository extends CrudRepository<Comment, Integer>{

	@Query(value = "SELECT coalesce(max(c.comment_id), 0) FROM comment c", nativeQuery = true)
	int getMaxCommentID();
	
	List<CommentDTO> findByPostId(Integer postId);
}
