package com.group4.DTO;

import java.math.BigDecimal;

public class CourseDTO {

	private Integer courseId;
	
	private String courseName;
	
	private String teacher;
	
	private Integer time;
	
	private String link;
	
	private BigDecimal price;
	
	private Integer discount;
	
	private BigDecimal discountPrice;
	
	private String type;
	
	private Integer postId;
	
	private boolean status;

	public CourseDTO(Integer courseId, String courseName, String teacher, Integer time, String link, BigDecimal price,
			Integer discount, BigDecimal discountPrice, String type, Integer postId, boolean status) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
		this.teacher = teacher;
		this.time = time;
		this.link = link;
		this.price = price;
		this.discount = discount;
		this.discountPrice = discountPrice;
		this.type = type;
		this.postId = postId;
		this.status = status;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public BigDecimal getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(BigDecimal discountPrice) {
		this.discountPrice = discountPrice;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

}
