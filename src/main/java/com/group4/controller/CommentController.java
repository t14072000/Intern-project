package com.group4.controller;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.group4.DTO.CommentDTO;
import com.group4.DTO.PostDTO;
import com.group4.service.CommentService;
import com.group4.service.PostService;

@Controller
public class CommentController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private CommentService commentService;
	
	@RequestMapping(value = "/post-comment", method = RequestMethod.POST)
	@ResponseBody
	public String postComment(@RequestParam("txtName") String username, @RequestParam("txtEmail") String email,
			@RequestParam("editor") String editor, @RequestParam(value = "txtPostID", required = true) String postId) {	
		PostDTO postDTO = null;
		try {
			postDTO = postService.findByPostIDAndStatus(Integer.parseInt(postId), true);
		} catch(NumberFormatException ex) {
			return "Post not existed";
		}
		if(postDTO == null) {
			return "Post not existed";
		}
		int commentID = commentService.getMaxCommentID()+1;
		
		long millis = System.currentTimeMillis();
        Timestamp datetime = new Timestamp(millis);
        
        CommentDTO commentDTO = new CommentDTO(commentID, username, email, datetime, 0, Integer.parseInt(postId), editor);
        boolean result = commentService.createComment(commentDTO);
        if(!result) {
        	return "FAIL";
        }
		return "SUCCESS";
	}
}
