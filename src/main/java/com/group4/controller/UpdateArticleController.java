package com.group4.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.security.Principal;
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
import com.group4.DTO.PostDTO;
import com.group4.DTO.TemplateDTO;
import com.group4.service.CategoryService;
import com.group4.service.PostService;
import com.group4.service.TemplateService;

@Controller
@RequestMapping(value = "/admin")
public class UpdateArticleController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private TemplateService templateService;
	
	@RequestMapping(value = "/search-article")
	public String searchArticlePage(Model model, @RequestParam(required = false, name = "txtTitle") String title,
			@RequestParam(required = false, name = "txtCategory") String categoryName, Principal principal) {
		model.addAttribute("username", principal.getName());
		
		List<CategoryDTO> categoryDTOList = categoryService.findByStatus(true);
		model.addAttribute("categoryList", categoryDTOList);
		if(categoryName != null) {
			Integer catID = categoryService.findByCategoryName(categoryName).getCategoryId();
			List<PostDTO> postDTOList = postService.findByTitleContainingAndCategoryId(title, catID);
			model.addAttribute("postDTOList", postDTOList);
			model.addAttribute("categoryName", categoryName);
			model.addAttribute("searchTitle", title);
		}
		return "search-article";
	}
	
	@RequestMapping(value = "/update-article/{postID}")
	public String updateArticlePage(Model model, @PathVariable("postID") String postID, Principal principal) {
		model.addAttribute("username", principal.getName());
		
		PostDTO postDTO = null;
		
		try {
			postDTO = postService.findByPostId(Integer.parseInt(postID));
		} catch(NumberFormatException ex) {
			return "redirect:/search-article";
		}
		
		if(postDTO == null) {
			return "redirect:/search-article";
		}
		model.addAttribute("post", postDTO);
		
		CategoryDTO categoryDTO = categoryService.findByCategoryId(postDTO.getCategoryId());
		String categoryName = "";
		if(categoryDTO != null) {
			categoryName = categoryDTO.getCategoryName();
		}
		model.addAttribute("categoryName", categoryName);
		
		List<CategoryDTO> categoryDTOList = categoryService.findByStatus(true);
		model.addAttribute("categoryList", categoryDTOList);
		
		String templateName = templateService.findByPostId(Integer.parseInt(postID)).getTemplateName();
		model.addAttribute("templateName", templateName);
		
		model.addAttribute("templateList", templateService.findAll());
		
		return "update-article";
	}
	
	@RequestMapping(value = "/update-article", method = RequestMethod.POST)
	@ResponseBody
	public String updateArticle(Model model, @RequestParam("txtPostID") String postID, @RequestParam("txtTitle") String title,
			@RequestParam("txtTemplate") String templateName, @RequestParam("txtCategory") String categoryName,
			@RequestParam("txtDescription") String description, @RequestParam("fileUp") MultipartFile image, 
			@RequestParam(value = "Status", required = false) String status, @RequestParam("editor") String postContent, 
			HttpServletRequest request) {
		CategoryDTO categoryDTO = categoryService.findByCategoryName(categoryName);
		if(categoryDTO == null && categoryName.trim().length() != 0) {
			return "Category not existed";
		}
		
		TemplateDTO templateDTO = templateService.findByTemplateName(templateName);
		if(templateDTO == null) {
			return "Template not existed";
		}
		
		PostDTO oldPostDTO = postService.findByPostId(Integer.parseInt(postID));
		boolean postStatus = false;
		if(status != null) {
			postStatus = true;
		}
		
		String imageName = "";
		String imageExtension = null;
		if(!image.isEmpty()) {
			imageName = StringUtils.cleanPath(image.getOriginalFilename());
			imageExtension = imageName.substring(imageName.lastIndexOf("."));
			imageName = postID + imageExtension;
		}
		else {
			imageName = oldPostDTO.getImage();
		}
		
		PostDTO postDTO = null;
		if(categoryName.trim().length() != 0) {
			postDTO = new PostDTO(oldPostDTO.getPostId(), title, oldPostDTO.getDate(), description,
					imageName, postContent, oldPostDTO.getAuthor(), categoryDTO.getCategoryId(), templateDTO.getTemplateId(), postStatus);
		}
		else {
			postDTO = new PostDTO(oldPostDTO.getPostId(), title, oldPostDTO.getDate(), description,
					imageName, postContent, oldPostDTO.getAuthor(), null, templateDTO.getTemplateId(), postStatus);
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
		return "SUCCESS";
	}
}
