package com.group4.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.security.Principal;
import java.util.List;
import java.util.Map;

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
import com.group4.DTO.PostDTO;
import com.group4.DTO.TemplateDTO;
import com.group4.service.CategoryService;
import com.group4.service.PostService;
import com.group4.service.TemplateService;

@Controller
@RequestMapping(value = "/admin")
public class UpdateCategoryController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private TemplateService templateService;
	
	@RequestMapping(value = "/search-category")
	public String searchCategoryPage(Model model, Principal principal) {
		model.addAttribute("username", principal.getName());
		
		List <CategoryDTO> categoryDTOList = categoryService.findAll();
		model.addAttribute("categoryDTOList", categoryDTOList);
		
		Map<Integer,String> categoryMapper = categoryService.getCategoryIdAndNameMapper();
		model.addAttribute("categoryMapper", categoryMapper);
		
		return "search-category";
	}
	
	@RequestMapping(value = "/update-category/{categoryID}")
	public String updateCategoryPage(Model model, @PathVariable("categoryID") String categoryID, Principal principal) {
		model.addAttribute("username", principal.getName());
		
		CategoryDTO categoryDTO = null;
		try {
			categoryDTO = categoryService.findByCategoryId(Integer.parseInt(categoryID));
		} catch(NumberFormatException ex) {
			return "redirect:/search-category";
		}
		
		if(categoryDTO == null) {
			return "redirect:/search-category";
		}
		model.addAttribute("categoryDTO", categoryDTO);
		
		String parentCategoryName = "";
		if(categoryDTO.getPreCategoryId() != null){
			parentCategoryName = categoryService.findByCategoryId(categoryDTO.getPreCategoryId()).getCategoryName();
		}
		model.addAttribute("parentCategoryName", parentCategoryName);
		
		List<CategoryDTO> categoryDTOList = categoryService.findByStatus(true);
		model.addAttribute("categoryList", categoryDTOList);
		
		PostDTO postDTO = postService.findByPostId(categoryDTO.getPostId());
		model.addAttribute("post", postDTO);
		
		String templateName = templateService.findByPostId(postDTO.getPostId()).getTemplateName();
		model.addAttribute("templateName", templateName);
		
		model.addAttribute("templateList", templateService.findAll());
		
		return "update-category";
	}
	
	@RequestMapping(value = "/update-category", method = RequestMethod.POST)
	@ResponseBody
	public String updateCategory(@RequestParam("txtCategoryID") String categoryID, @RequestParam("txtCategoryName") String categoryName,
			@RequestParam("txtDescription") String description, @RequestParam("fileUp") MultipartFile image, 
			@RequestParam("txtParentCategory") String parentCategoryName, @RequestParam("txtTemplate") String templateName,
			@RequestParam("txtPath") String path, HttpServletRequest request, @RequestParam("editor") String postContent,
			@RequestParam(value = "Status", required = false) String status) {	
		CategoryDTO categoryDTO = categoryService.findByCategoryName(categoryName);
		if(categoryDTO != null) {
			if(categoryDTO.getCategoryId() != Integer.parseInt(categoryID)) {
				return "Category already existed";
			}
		}
		
		categoryDTO = categoryService.findByPath(path);
		if(categoryDTO != null) {
			if(categoryDTO.getCategoryId() != Integer.parseInt(categoryID)) {
				return "Path already existed";
			}
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
		
		categoryDTO = categoryService.findByCategoryId(Integer.parseInt(categoryID));
					
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
		
		PostDTO postDTO = null;
		if(parentCategoryDTO != null) {
			postDTO = new PostDTO(categoryDTO.getPostId(), categoryName, oldPostDTO.getDate(), description, imageName, postContent,
					oldPostDTO.getAuthor(), parentCategoryDTO.getCategoryId(),templateDTO.getTemplateId(), postStatus);
		}
		else {
			postDTO = new PostDTO(categoryDTO.getPostId(), categoryName, oldPostDTO.getDate(), description, imageName, postContent,
					oldPostDTO.getAuthor(), null,templateDTO.getTemplateId(), postStatus);
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
		
		CategoryDTO createCategoryDTO = null;
		if(parentCategoryDTO != null) {
			createCategoryDTO = new CategoryDTO(Integer.parseInt(categoryID), categoryName,
					parentCategoryDTO.getCategoryId(), path, categoryDTO.getPostId(), postStatus) ;
		}
		else {
			createCategoryDTO = new CategoryDTO(Integer.parseInt(categoryID), categoryName, null, path, categoryDTO.getPostId(), postStatus) ;
		}
		
		result = categoryService.createUpdateCategory(createCategoryDTO);
		if (!result) {
			return "FAIL";
		}
		//create category end
		return "SUCCESS";
	}
}
