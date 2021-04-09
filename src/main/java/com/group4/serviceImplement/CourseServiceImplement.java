package com.group4.serviceImplement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group4.DTO.CourseDTO;
import com.group4.entity.Course;
import com.group4.repo.CourseRepository;
import com.group4.service.CourseService;

@Service
public class CourseServiceImplement implements CourseService{
	
	@Autowired
	private CourseRepository courseRepo;

	@Autowired
	private ModelMapper mapper;
	
	@Override
	public boolean createUpdateCourse(CourseDTO courseDTO) {
		// TODO Auto-generated method stub
		try {
			Course course = mapper.map(courseDTO, Course.class);
			courseRepo.save(course);
			return true;
		}
		catch(IllegalArgumentException ex) {
			return false;
		}
	}

	@Override
	public int getMaxCategoryID() {
		// TODO Auto-generated method stub
		return courseRepo.getMaxCourseID();
	}

	@Override
	public List<CourseDTO> findAll() {
		// TODO Auto-generated method stub
		List <Course> courseList = courseRepo.findAll();
		List<CourseDTO> courseDTOList = new ArrayList<CourseDTO>();
		for (Iterator<Course> iterator = courseList.iterator(); iterator.hasNext();) {
			Course course = (Course) iterator.next();
			CourseDTO courseDTO = new CourseDTO(course.getCourseId(),course.getCourseName(),course.getTeacher(),course.getTime(),
					course.getLink(),course.getPrice(),course.getDiscount(),course.getDiscountPrice(),course.getType(),
					course.getPostId(),course.isStatus());
			courseDTOList.add(courseDTO);
		}
		return courseDTOList;
	}

	@Override
	public List<CourseDTO> findByStatus(boolean status) {
		// TODO Auto-generated method stub
		return courseRepo.findByStatus(status);
	}

	@Override
	public CourseDTO findByCourseId(Integer courseId) {
		// TODO Auto-generated method stub
		return courseRepo.findByCourseId(courseId);
	}

	@Override
	public CourseDTO findByPostId(Integer postId) {
		// TODO Auto-generated method stub
		return courseRepo.findByPostId(postId);
	}

	@Override
	public CourseDTO findByPostIdAndStatus(Integer postId, boolean status) {
		// TODO Auto-generated method stub
		return courseRepo.findByPostIdAndStatus(postId, status);
	}
	
}
