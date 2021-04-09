package com.group4.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.security.Principal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
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
public class CreateCourseController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private TemplateService templateService;
	
	@Autowired
	private CourseService courseService;

	
	@RequestMapping(value = "/create-course")
	public String createCoursePage(Model model, Principal principal) {
		model.addAttribute("username", principal.getName());
		
		List<CategoryDTO> categoryDTOList = categoryService.findByStatus(true);
		model.addAttribute("categoryList", categoryDTOList);
		
		model.addAttribute("templateList", templateService.findAll());
		return "create-course";
	}
	
	@RequestMapping(value = "/create-course", method = RequestMethod.POST)
	@ResponseBody
	public String createCourse(@RequestParam("txtCourseName") String courseName, @RequestParam("txtTeacher") String teacher, 
			@RequestParam("txtTime") String time, @RequestParam("txtLink") String link, @RequestParam("txtPrice") String txtPrice,
			@RequestParam("txtDiscount") String discount, @RequestParam("txtDiscountPrice") String txtDiscountPrice,
			@RequestParam("txtType") String type, @RequestParam("txtDescription") String description,
			@RequestParam("txtCategory") String categoryName, @RequestParam("fileUp") MultipartFile image, 
			@RequestParam("txtTemplate") String templateName, @RequestParam("editor") String postContent, HttpServletRequest request
			, Principal principal) {
		CategoryDTO categoryDTO = categoryService.findByCategoryName(categoryName);
		if(categoryDTO == null && categoryName.trim().length() != 0) {
			return "Category not existed";
		}
		TemplateDTO templateDTO = templateService.findByTemplateName(templateName);
		if(templateDTO == null) {
			return "Template not existed";
		}
		//create post start
		int postID = postService.getMaxPostID() + 1; 
			
		long millis=System.currentTimeMillis();  
		Date date = new Date(millis);
			
		String imageName = "";
		String imageExtension = null;
		if(!image.isEmpty()) {
			imageName = StringUtils.cleanPath(image.getOriginalFilename());
			imageExtension = imageName.substring(imageName.lastIndexOf("."));
			imageName = postID + imageExtension;
		}
			
		PostDTO postDTO = new PostDTO(postID, courseName, date, description, imageName, postContent,
				principal.getName(), categoryDTO.getCategoryId(),templateDTO.getTemplateId(), true);
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
		int courseID = courseService.getMaxCategoryID() + 1;
		
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
		CourseDTO courseDTO = new CourseDTO(courseID, courseName, teacher, timeAmount, link, price, discountAmount,
				discountPrice, type, postID, true);
		result = courseService.createUpdateCourse(courseDTO);
		if (!result) {
			return "FAIL";
		}
		return "SUCCESS";
	}
}
