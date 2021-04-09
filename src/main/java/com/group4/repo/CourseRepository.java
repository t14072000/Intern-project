package com.group4.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.group4.DTO.CourseDTO;
import com.group4.entity.Course;

public interface CourseRepository extends CrudRepository<Course, Integer>{
	
	@Query(value = "SELECT coalesce(max(c.course_id), 0) FROM course c", nativeQuery = true)
	int getMaxCourseID();
	
	List<Course> findAll();
	
	List<CourseDTO> findByStatus(boolean status);
	
	CourseDTO findByCourseId(Integer courseId);
	
	CourseDTO findByPostId(Integer postId);
	
	CourseDTO findByPostIdAndStatus(Integer postId, boolean status);
}
