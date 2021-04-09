package com.group4.serviceImplement;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group4.DTO.CommentDTO;
import com.group4.entity.Comment;
import com.group4.repo.CommentRepository;
import com.group4.service.CommentService;

@Service
public class CommentServiceImplement implements CommentService{
	
	@Autowired
	private CommentRepository commentRepo;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public boolean createComment(CommentDTO commentDTO) {
		// TODO Auto-generated method stub
		try {
			Comment comment = mapper.map(commentDTO, Comment.class);
			commentRepo.save(comment);
			return true;
		}
		catch(IllegalArgumentException ex) {
			return false;
		}
	}

	@Override
	public int getMaxCommentID() {
		// TODO Auto-generated method stub
		return commentRepo.getMaxCommentID();
	}

	@Override
	public List<CommentDTO> findByPostId(Integer postId) {
		// TODO Auto-generated method stub
		return commentRepo.findByPostId(postId);
	}

	

}
