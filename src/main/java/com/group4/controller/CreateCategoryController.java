package com.group4.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.security.Principal;
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
import com.group4.DTO.PostDTO;
import com.group4.DTO.TemplateDTO;
import com.group4.service.CategoryService;
import com.group4.service.PostService;
import com.group4.service.TemplateService;

@Controller
@RequestMapping(value = "/admin")
public class CreateCategoryController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private TemplateService templateService;
	
	@RequestMapping(value = "/create-category")
	public String createCategoryPage(Model model, Principal principal) {
		model.addAttribute("username", principal.getName());
		
		List<CategoryDTO> categoryDTOList = categoryService.findByStatus(true);
		model.addAttribute("categoryList", categoryDTOList);
		
		model.addAttribute("templateList", templateService.findAll());
		return "create-category";
	}
	
	@RequestMapping(value = "/create-category", method = RequestMethod.POST)
	@ResponseBody
	public String createCategory(@RequestParam("txtCategoryName") String categoryName, @RequestParam("txtDescription") String description,
			@RequestParam("fileUp") MultipartFile image, @RequestParam("txtParentCategory") String parentCategoryName,
			@RequestParam("txtTemplate") String templateName, @RequestParam("txtPath") String path, HttpServletRequest request,
			@RequestParam("editor") String postContent, Principal principal) {	
		
		CategoryDTO categoryDTO = categoryService.findByCategoryName(categoryName);
		if(categoryDTO != null) {
			return "Category already existed";
		}
		
		categoryDTO = categoryService.findByPath(path);
		if(categoryDTO != null) {
			return "Path already existed";
		}
		
		CategoryDTO parentCategoryDTO = null;
		if(parentCategoryName.trim().length() != 0) {
			parentCategoryDTO = categoryService.findByCategoryName(parentCategoryName);
			if(parentCategoryDTO == null) {
				return "Parent Category not existed";
			}
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
		
		PostDTO postDTO = null;
		if(parentCategoryDTO != null) {
			postDTO = new PostDTO(postID, categoryName, date, description, imageName, postContent,
				principal.getName(), parentCategoryDTO.getCategoryId(),templateDTO.getTemplateId(), true);
		}
		else {
			postDTO = new PostDTO(postID, categoryName, date, description, imageName, postContent,
					principal.getName(), null,templateDTO.getTemplateId(), true);
		}
		
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
		//create category
		int categoryID = categoryService.getMaxCategoryID() + 1;
		
		CategoryDTO createCategoryDTO = null;
		if(parentCategoryDTO != null) {
			createCategoryDTO = new CategoryDTO(categoryID, categoryName, parentCategoryDTO.getCategoryId(), path, postID, true) ;
		}
		else {
			createCategoryDTO = new CategoryDTO(categoryID, categoryName, null, path, postID, true) ;
		}
		
		result = categoryService.createUpdateCategory(createCategoryDTO);
		if (!result) {
			return "FAIL";
		}
		//create category end
		return "SUCCESS";
	}
}
