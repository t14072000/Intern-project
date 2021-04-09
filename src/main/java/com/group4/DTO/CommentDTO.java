package com.group4.DTO;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class CommentDTO {
    private Integer commentId;
    
    private String username;
    
    private String email;
    
    @Temporal(TemporalType.TIMESTAMP)
	private Date datetime;
    
    private Integer likeTotal;
    
    private Integer postId;
    
    private String commentContent;

	public CommentDTO(Integer commentId, String username, String email, Date datetime, Integer likeTotal,
			Integer postId, String commentContent) {
		super();
		this.commentId = commentId;
		this.username = username;
		this.email = email;
		this.datetime = datetime;
		this.likeTotal = likeTotal;
		this.postId = postId;
		this.commentContent = commentContent;
	}

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public Integer getLikeTotal() {
		return likeTotal;
	}

	public void setLikeTotal(Integer likeTotal) {
		this.likeTotal = likeTotal;
	}

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	
}
