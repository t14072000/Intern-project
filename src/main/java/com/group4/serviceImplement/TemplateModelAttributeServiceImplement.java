package com.group4.serviceImplement;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import com.group4.DTO.CategoryDTO;
import com.group4.DTO.CommentDTO;
import com.group4.DTO.ConversionDTO;
import com.group4.DTO.CourseDTO;
import com.group4.DTO.OrderDTO;
import com.group4.DTO.PostDTO;
import com.group4.service.CategoryService;
import com.group4.service.CommentService;
import com.group4.service.CourseService;
import com.group4.service.OrderDetailService;
import com.group4.service.OrderService;
import com.group4.service.PostService;
import com.group4.service.TemplateModelAttributeService;

@Service
public class TemplateModelAttributeServiceImplement implements TemplateModelAttributeService{

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private PostService postService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private OrderDetailService orderDetailService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public Model getPostTemplateModel(Model model, PostDTO post, CategoryDTO category) {
		// TODO Auto-generated method stub
		try {
			model.addAttribute("link", category.getPath());
			List<CategoryDTO> categoryList = categoryService.findByPreCategoryId(category.getPreCategoryId());
			model.addAttribute("categoryList", categoryList);
			
			List<CommentDTO> commentList = commentService.findByPostId(post.getPostId());
			if(commentList.size() != 0) {
				model.addAttribute("commentList", commentList);
			}
			
			if(category != null) {
				List<PostDTO> postList =  postService.findByCategoryId(category.getCategoryId());
				model.addAttribute("postList", postList);
			}
		} 
		catch (Exception e) {
			return null;
		}
		return model;
	}

	@Override
	public Model getCourseTemplateModel(Model model, PostDTO post, CategoryDTO category) {
		// TODO Auto-generated method stub
		try {
			final String CONVERSION_URL = "https://free.currconv.com/api/v7/convert?q=VND_USD&compact=ultra&apiKey=d3116fd48322f63d98b1";
			
			model.addAttribute("link", category.getPath());
			List<CategoryDTO> categoryList = categoryService.findByPreCategoryId(category.getPreCategoryId());
			model.addAttribute("categoryList", categoryList);
			
			List<CommentDTO> commentList = commentService.findByPostId(post.getPostId());
			if(commentList.size() != 0) {
				model.addAttribute("commentList", commentList);
			}
			
			CourseDTO courseDTO = courseService.findByPostId(post.getPostId());
			model.addAttribute("courseDTO",courseDTO);
			
			List<Integer> orderIdList = orderDetailService.getTop10OrdersIdByPostId(courseDTO.getCourseId());
			List<OrderDTO> orderList = orderService.findByOrderIdIn(orderIdList);
			model.addAttribute("orderList",orderList);
			
			model.addAttribute("bankPayment",courseDTO.getDiscountPrice());
			model.addAttribute("postPayment",courseDTO.getDiscountPrice().add(BigDecimal.valueOf(25000)));
			ConversionDTO conversionRate = restTemplate.getForObject(CONVERSION_URL, ConversionDTO.class);
			BigDecimal paypalPayment = courseDTO.getDiscountPrice().multiply(conversionRate.getVND_USD()).multiply(BigDecimal.valueOf(1.15));
			model.addAttribute("paypalPayment", paypalPayment);
		}
		catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return model;
	}

	@Override
	public Model getCategory1Model(Model model, PostDTO post) {
		// TODO Auto-generated method stub
		try {
			CategoryDTO categoryPost = categoryService.findByPostId(post.getPostId());
			model.addAttribute("link", categoryPost.getPath());
			List<CategoryDTO> categoryList = categoryService.findByPreCategoryId(categoryPost.getCategoryId());
			model.addAttribute("categoryList", categoryList);
			
			Map<String,Object> categoryMapping = new HashMap<String,Object>();
			for(int i = 0 ; i < categoryList.size(); i++) {
				Map<String,PostDTO> subCategoryMapping = new HashMap<String,PostDTO>();
				List<CategoryDTO> subCategoryList = 
						categoryService.findByPreCategoryId(categoryList.get(i).getCategoryId());
				
				for(int j = 0; j < subCategoryList.size(); j++) {
					subCategoryMapping.put(subCategoryList.get(j).getPath(), 
							postService.findByPostId(subCategoryList.get(j).getPostId()));
				}
				
				categoryMapping.put(categoryList.get(i).getCategoryName(), subCategoryMapping);
			}
			model.addAttribute("categoryMapping", categoryMapping);
		}
		catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return model;
	}

	@Override
	public Model getCategory2Model(Model model, PostDTO post, String pageString) {
		// TODO Auto-generated method stub
		try {
			CategoryDTO categoryPost = categoryService.findByPostId(post.getPostId());
			model.addAttribute("link", categoryPost.getPath());
			model.addAttribute("category", categoryPost);
			List<CategoryDTO> categoryList = null;
			if(categoryPost.getPreCategoryId()!= null) {
				categoryList = categoryService.findByPreCategoryId(categoryPost.getPreCategoryId());
			}
			else {
				categoryList = categoryService.findByPreCategoryId(categoryPost.getCategoryId());
			}
			model.addAttribute("categoryList", categoryList);
			
			List<CategoryDTO> subCategoryList = categoryService.findByPreCategoryId(categoryPost.getCategoryId());
			Map<String,Object> categoryMapping = new HashMap<String,Object>();
			for(int i = 0 ; i < subCategoryList.size(); i++) {
				categoryMapping.put(subCategoryList.get(i).getPath(),postService.findByPostId(subCategoryList.get(i).getPostId()));
			}
			model.addAttribute("categoryMapping", categoryMapping);
			
			List<Integer> cIdList = new ArrayList<Integer>();
			cIdList = categoryService.findAllSubCatId(categoryPost.getCategoryId());
			if (cIdList.size() == 0) {
				cIdList.add(categoryPost.getCategoryId());
			}

			Integer page = Integer.valueOf(pageString);
			if(page < 1) {
				return null;
			}
			
			Pageable pageable = (Pageable) PageRequest.of(page - 1, 12);
			Page<PostDTO> listPost = postService.findByCategoryIdInOrderByPostIdDesc(cIdList, pageable);
			
			if(listPost == null) {
				return null;
			}
			
			if(listPost.getTotalPages() != 0 && page > listPost.getTotalPages()) {
				return null;
			}
			
			model.addAttribute("listPost", listPost);
		}
		catch (Exception ex) {
			// TODO: handle exception
			return null;
		}
		return model;
	}

	@Override
	public Model getCategory3Model(Model model, PostDTO post, CategoryDTO category) {
		// TODO Auto-generated method stub
		try {
			List<CategoryDTO> categoryList = categoryService.findByPreCategoryId(category.getCategoryId());
			model.addAttribute("categoryList", categoryList);
			
			Map<String,Object> categoryMapping = new HashMap<String,Object>();
			for(int i = 0 ; i < categoryList.size(); i++) {
				categoryMapping.put(categoryList.get(i).getPath(),postService.findByPostId(categoryList.get(i).getPostId()));
			}
			model.addAttribute("categoryMapping", categoryMapping);
			
			CategoryDTO categoryPost = categoryService.findByPostId(post.getPostId());
			model.addAttribute("link", categoryPost.getPath());
			if(categoryPost != null) {
				List<PostDTO> postList =  postService.findByCategoryId(categoryPost.getCategoryId());
				model.addAttribute("postList", postList);
			}
		}
		catch (Exception ex) {
			// TODO: handle exception
			return null;
		}
		return model;
	}

	@Override
	public Model getCourseCategory1Model(Model model, PostDTO post, String pageString) {
		// TODO Auto-generated method stub
		try {
			CategoryDTO categoryPost = categoryService.findByPostId(post.getPostId());
			model.addAttribute("link", categoryPost.getPath());
			model.addAttribute("category", categoryPost);
			List<CategoryDTO> categoryList = categoryService.findByPreCategoryId(categoryPost.getCategoryId());
			model.addAttribute("categoryList", categoryList);
			
			List<Integer> cIdList = new ArrayList<Integer>();
			cIdList = categoryService.findAllSubCatId(categoryPost.getCategoryId());
			if (cIdList.size() == 0) {
				cIdList.add(categoryPost.getCategoryId());
			}
			Map<String,Map<PostDTO,CourseDTO>> categoryCourseMapping = new HashMap<String,Map<PostDTO,CourseDTO>>();
			for (int i = 0; i < cIdList.size(); i++) {
				Map<PostDTO,CourseDTO> postCourseMapping = new HashMap<PostDTO,CourseDTO>();
				List <PostDTO> postList = postService.findByCategoryId(cIdList.get(i));
				for (int j = 0; j < postList.size(); j++) {
					Integer postId = postList.get(j).getPostId();
					CourseDTO courseTmp = courseService.findByPostIdAndStatus(postId, true);
					if(courseTmp != null) {
						postCourseMapping.put(postList.get(j), courseTmp);
					}
				}
				categoryCourseMapping.put(categoryService.findByCategoryId(cIdList.get(i)).getCategoryName(), postCourseMapping);
			}
			model.addAttribute("categoryCourseMapping",categoryCourseMapping);
			
			Integer page = Integer.valueOf(pageString);
			if(page < 1) {
				return null;
			}
				
			Pageable pageable = (Pageable) PageRequest.of(page - 1, 12);
			Page<PostDTO> listPost = postService.findByCategoryIdInOrderByPostIdDesc(cIdList, pageable);
				
			if(listPost == null) {
				return null;
			}
				
			if(listPost.getTotalPages() != 0 && page > listPost.getTotalPages()) {
				return null;
			}
			model.addAttribute("listPost", listPost);
		}
		catch (Exception ex) {
			// TODO: handle exception
			return null;
		}
		return model;
	}

	@Override
	public Model getCourseCategory2Model(Model model, PostDTO post, String pageString) {
		// TODO Auto-generated method stub
		try {
			CategoryDTO categoryPost = categoryService.findByPostId(post.getPostId());
			model.addAttribute("link", categoryPost.getPath());
			model.addAttribute("category", categoryPost);
			List<CategoryDTO> categoryList = categoryService.findByPreCategoryId(categoryPost.getPreCategoryId());
			model.addAttribute("categoryList", categoryList);
			
			Map<String,Map<PostDTO,CourseDTO>> categoryCourseMapping = new HashMap<String,Map<PostDTO,CourseDTO>>();
			Map<PostDTO,CourseDTO> postCourseMapping = new HashMap<PostDTO,CourseDTO>();
			List <PostDTO> postList = postService.findByCategoryId(categoryPost.getCategoryId());
			for (int i = 0; i < postList.size(); i++) {
				Integer postId = postList.get(i).getPostId();
				CourseDTO courseTmp = courseService.findByPostIdAndStatus(postId, true);
				if(courseTmp != null) {
					postCourseMapping.put(postList.get(i), courseTmp);
				}
			}
			categoryCourseMapping.put(categoryPost.getCategoryName(), postCourseMapping);
			model.addAttribute("categoryCourseMapping",categoryCourseMapping);
			
			List<CategoryDTO> subCategoryList = categoryService.findByPreCategoryId(categoryPost.getCategoryId());
			Map<String,Object> categoryMapping = new HashMap<String,Object>();
			for(int i = 0 ; i < subCategoryList.size(); i++) {
				categoryMapping.put(subCategoryList.get(i).getPath(),postService.findByPostId(subCategoryList.get(i).getPostId()));
			}
			model.addAttribute("categoryMapping", categoryMapping);
			
			List<Integer> cIdList = new ArrayList<Integer>();
			cIdList = categoryService.findAllSubCatId(categoryPost.getCategoryId());
			if (cIdList.size() == 0) {
				cIdList.add(categoryPost.getCategoryId());
			}
			
			Integer page = Integer.valueOf(pageString);
			if(page < 1) {
				return null;
			}
			
			Pageable pageable = (Pageable) PageRequest.of(page - 1, 12);
			Page<PostDTO> listPost = postService.findByCategoryIdInOrderByPostIdDesc(cIdList, pageable);
			if(listPost == null) {
				return null;
			}
			
			if(listPost.getTotalPages() != 0 && page > listPost.getTotalPages()) {
				return null;
			}
			
			model.addAttribute("listPost", listPost);
		}
		catch (Exception ex) {
			// TODO: handle exception
			return null;
		}
		return model;
	}

	@Override
	public Model getDiscountTemplateModel(Model model, PostDTO post) {
		// TODO Auto-generated method stub
		try {
			List<CommentDTO> commentList = commentService.findByPostId(post.getPostId());
			if(commentList.size() != 0) {
				model.addAttribute("commentList", commentList);
			}
		}
		catch (Exception ex) {
			// TODO: handle exception
			return null;
		}
		return model;
	}

}
