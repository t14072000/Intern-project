package com.group4.service;

import java.util.List;

import com.group4.DTO.CourseDTO;

public interface CourseService {
	
	int getMaxCategoryID();

	boolean createUpdateCourse(CourseDTO courseDTO);
	
	List<CourseDTO> findAll();
	
	List<CourseDTO> findByStatus(boolean status);
	
	CourseDTO findByCourseId(Integer courseId);
	
	CourseDTO findByPostId(Integer postId);
	
	CourseDTO findByPostIdAndStatus(Integer postId, boolean status);
}
