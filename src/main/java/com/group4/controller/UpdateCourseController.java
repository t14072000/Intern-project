package com.group4.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.security.Principal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.group4.DTO.CategoryDTO;
import com.group4.DTO.CourseDTO;
import com.group4.DTO.PostDTO;
import com.group4.DTO.TemplateDTO;
import com.group4.service.CategoryService;
import com.group4.service.CourseService;
import com.group4.service.PostService;
import com.group4.service.TemplateService;

@Controller
@RequestMapping(value = "/admin")
public class UpdateCourseController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private TemplateService templateService;
	
	@Autowired
	private CourseService courseService;
	
	@RequestMapping(value = "/search-course")
	public String searchCoursePage(Model model, Principal principal) {
		model.addAttribute("username", principal.getName());
		
		List <CourseDTO> courseDTOList = courseService.findAll();
		model.addAttribute("courseDTOList", courseDTOList);
		
		return "search-course";
	}
	
	@RequestMapping(value = "/update-course/{courseID}")
	public String updateCoursePage(Model model, @PathVariable("courseID") String courseID, Principal principal) {
		model.addAttribute("username", principal.getName());
		
		CourseDTO courseDTO = null;
		try {
			courseDTO = courseService.findByCourseId(Integer.parseInt(courseID));
		} catch(NumberFormatException ex) {
			return "redirect:/search-course";
		}
		
		if(courseDTO == null) {
			return "redirect:/search-course";
		}
		model.addAttribute("courseDTO", courseDTO);
		
		PostDTO postDTO = postService.findByPostId(courseDTO.getPostId());
		model.addAttribute("postDTO", postDTO);
		
		CategoryDTO categoryDTO = categoryService.findByCategoryId(postDTO.getCategoryId());
		String categoryName = "";
		if(categoryDTO != null) {
			categoryName = categoryDTO.getCategoryName();
		}
		model.addAttribute("categoryName", categoryName);
		
		List<CategoryDTO> categoryDTOList = categoryService.findByStatus(true);
		model.addAttribute("categoryList", categoryDTOList);
		
		String templateName = templateService.findByPostId(postDTO.getPostId()).getTemplateName();
		model.addAttribute("templateName", templateName);
		
		model.addAttribute("templateList", templateService.findAll());
		
		return "update-course";
	}
	
	@RequestMapping(value = "/update-course", method = RequestMethod.POST)
	@ResponseBody
	public String updateCourse(@RequestParam("txtCourseId") String courseID, @RequestParam("txtCourseName") String courseName,
			@RequestParam("txtTeacher") String teacher, @RequestParam("txtTime") String time, @RequestParam("txtLink") String link,
			@RequestParam("txtPrice") String txtPrice, @RequestParam("txtDiscount") String discount, 
			@RequestParam("txtDiscountPrice") String txtDiscountPrice, @RequestParam("txtType") String type, 
			@RequestParam("txtDescription") String description, @RequestParam("txtCategory") String categoryName, 
			@RequestParam("fileUp") MultipartFile image, @RequestParam("txtTemplate") String templateName, 
			@RequestParam("editor") String postContent, @RequestParam(value = "Status", required = false) String status, 
			HttpServletRequest request) {	
		CategoryDTO categoryDTO = categoryService.findByCategoryName(categoryName);
		if(categoryDTO == null && categoryName.trim().length() != 0) {
			return "Category not existed";
		}
		
		TemplateDTO templateDTO = templateService.findByTemplateName(templateName);
		if(templateDTO == null) {
			return "Template not existed";
		}
		CourseDTO courseDTO = courseService.findByCourseId(Integer.parseInt(courseID));
		//create post start
		int postID = courseDTO.getPostId(); 
			
		PostDTO oldPostDTO = postService.findByPostId(categoryDTO.getPostId());
		boolean postStatus = false;
		if(status != null) {
			postStatus = true;
		}
		
		String imageName = "";
		String imageExtension = null;
		if(!image.isEmpty()) {
			imageName = StringUtils.cleanPath(image.getOriginalFilename());
			imageExtension = imageName.substring(imageName.lastIndexOf("."));
			imageName = categoryDTO.getPostId() + imageExtension;
		}
		else {
			imageName = oldPostDTO.getImage();
		}
			
		PostDTO postDTO = new PostDTO(postID, courseName, oldPostDTO.getDate(), description, imageName, postContent,
				oldPostDTO.getAuthor(), categoryDTO.getCategoryId(),templateDTO.getTemplateId(), postStatus);
		boolean result = postService.createUpdatePost(postDTO);
		if (!result) {
			return "FAIL";
		}
		
		if(!image.isEmpty()) {
			try {
				String uploadRootPath = request.getServletContext().getRealPath("/") + "image\\";
				File uploadRootDir = new File(uploadRootPath);
		        
		        if (!uploadRootDir.exists()) {
		            uploadRootDir.mkdirs();
		        }
				        
		        File serverFile = new File(uploadRootDir.getAbsolutePath() + "\\" +imageName);
		        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
	            stream.write(image.getBytes());
	            stream.close();
	                    
			}
			catch (Exception e) {
				return "FAIL";
			}
		}
		//create post end 
		
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setGroupingSeparator(',');
		dfs.setDecimalSeparator('.');
		String pattern = "#,##0.00";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, dfs);
		decimalFormat.setParseBigDecimal(true);

		BigDecimal price = null;
		BigDecimal discountPrice = null;
		try {
			price = (BigDecimal) decimalFormat.parse(txtPrice);
			discountPrice = (BigDecimal) decimalFormat.parse(txtDiscountPrice);
		} catch (ParseException e) {
			return "FAIL";
		}
		Integer discountAmount = null;
		if(!discount.trim().equals("")) {
			discountAmount = Integer.parseInt(discount);
		}
		Integer timeAmount = null;
		if(!time.trim().equals("")) {
			timeAmount = Integer.parseInt(time);
		}
		CourseDTO updateCourseDTO = new CourseDTO(Integer.parseInt(courseID), courseName, teacher, timeAmount, link, 
				price, discountAmount, discountPrice, type, postID, postStatus);
		
		result = courseService.createUpdateCourse(updateCourseDTO);
		if (!result) {
			return "FAIL";
		}
		return "SUCCESS";
	}
}
