package com.group4.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.group4.DTO.DiscountDTO;
import com.group4.DTO.PostDTO;
import com.group4.service.CategoryService;
import com.group4.service.CommentService;
import com.group4.service.DiscountService;
import com.group4.service.PostService;


@Controller
public class MainController {
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private PostService postService;

	@Autowired
	private DiscountService discountService;
	
	@Autowired
	CommentService commentService;
	
	@RequestMapping(value = "/")
	public String home(Model model) {
		Integer catID = null;

		List<Integer> cIdList = new ArrayList<Integer>();

		// coupon giam gia
		catID = categoryService.findByCategoryName("Coupon giảm giá").getCategoryId();
		cIdList = categoryService.findAllSubCatId(catID);
		if (cIdList.size() == 0)
			cIdList.add(catID);
		List<PostDTO> discountPostList = postService.getTop12PostByCategoryIdAndStatus(cIdList, true);
		model.addAttribute("discountPostList", discountPostList);

		// lap trinh
		catID = categoryService.findByCategoryName("Lập trình").getCategoryId();
		cIdList = categoryService.findAllSubCatId(catID);
		if (cIdList.size() == 0)
			cIdList.add(catID);
		List<PostDTO> programPostList = postService.getTop5PostByCategoryIdAndStatus(cIdList, true);
		model.addAttribute("programPostList", programPostList);

		// web mien phi 
		catID = categoryService.findByCategoryName("Web miễn phí").getCategoryId();
		cIdList = categoryService.findAllSubCatId(catID);
		if (cIdList.size() == 0)
			cIdList.add(catID);
		List<PostDTO> webPostList = postService.getTop5PostByCategoryIdAndStatus(cIdList, true);
		model.addAttribute("webPostList", webPostList);

		// quan tri web 
		catID = categoryService.findByCategoryName("Quản trị Web").getCategoryId();
		cIdList = categoryService.findAllSubCatId(catID);
		if (cIdList.size() == 0)
			cIdList.add(catID);
		List<PostDTO> webmastersPostList = postService.getTop5PostByCategoryIdAndStatus(cIdList, true);
		model.addAttribute("webmastersPostList", webmastersPostList);

		// tin hoc
		catID = categoryService.findByCategoryName("Tin học").getCategoryId();
		cIdList = categoryService.findAllSubCatId(catID);
		if (cIdList.size() == 0)
			cIdList.add(catID);
		List<PostDTO> informaticsPostList = postService.getTop5PostByCategoryIdAndStatus(cIdList, true);
		model.addAttribute("informaticsPostList", informaticsPostList);

		// thu thuat
		catID = categoryService.findByCategoryName("Thủ thuật").getCategoryId();
		cIdList = categoryService.findAllSubCatId(catID);
		if (cIdList.size() == 0)
			cIdList.add(catID);
		List<PostDTO> tipsPostList = postService.getTop5PostByCategoryIdAndStatus(cIdList, true);
		model.addAttribute("tipsPostList", tipsPostList);

		// download 
		catID = categoryService.findByCategoryName("Download").getCategoryId();
		cIdList = categoryService.findAllSubCatId(catID);
		if (cIdList.size() == 0)
			cIdList.add(catID);
		List<PostDTO> downloadPostList = postService.getTop5PostByCategoryIdAndStatus(cIdList, true);
		model.addAttribute("downloadPostList", downloadPostList);

		// marketing
		catID = categoryService.findByCategoryName("Marketing").getCategoryId();
		cIdList = categoryService.findAllSubCatId(catID);
		if (cIdList.size() == 0)
			cIdList.add(catID);
		List<PostDTO> marketingPostList = postService.getTop5PostByCategoryIdAndStatus(cIdList, true);
		model.addAttribute("marketingPostList", marketingPostList);

		// kham pha 
		catID = categoryService.findByCategoryName("Khám phá").getCategoryId();
		cIdList = categoryService.findAllSubCatId(catID);
		if (cIdList.size() == 0)
			cIdList.add(catID);
		List<PostDTO> discoverPostList = postService.getTop5PostByCategoryIdAndStatus(cIdList, true);
		model.addAttribute("discoverPostList", discoverPostList);

		// mon hoc
		catID = categoryService.findByCategoryName("Môn học").getCategoryId();
		cIdList = categoryService.findAllSubCatId(catID);
		if (cIdList.size() == 0)
			cIdList.add(catID);
		List<PostDTO> studyPostList = postService.getTop5PostByCategoryIdAndStatus(cIdList, true);
		model.addAttribute("studyPostList", studyPostList);

		// review khoa hoc 
		catID = categoryService.findByCategoryName("Khóa học").getCategoryId();
		cIdList = categoryService.findAllSubCatId(catID);
		if (cIdList.size() == 0)
			cIdList.add(catID);
		List<PostDTO> coursePostList = postService.getTop5PostByCategoryIdAndStatus(cIdList, true);
		model.addAttribute("coursePostList", coursePostList);

		// bai tap
		cIdList = categoryService.findCategoryIDByCategoryNameContaining("Bài tập");
		List<PostDTO> exercisePostList = postService.getTop20PostByCategoryIdAndStatus(cIdList, true);
		model.addAttribute("exercisePostList", exercisePostList);

		// coupon list 
		List<DiscountDTO> couponList = discountService.findByStatus(true);
		model.addAttribute("couponList", couponList);

		return "mainpage";
	}
	
	@RequestMapping("/gioi-thieu")
	public String gioiThieuPage(Model model) {
		List<DiscountDTO> couponList = discountService.findAll();
		model.addAttribute("couponList", couponList);
		return "gioi-thieu";
	}
	
	@RequestMapping("/dieu-khoan-su-dung")
	public String dieuKhoanSuDungPage(Model model) {
		List<DiscountDTO> couponList = discountService.findAll();
		model.addAttribute("couponList", couponList);
		return "dieu-khoan-su-dung";
	}
	
	@RequestMapping("/chinh-sach-bao-mat")
	public String chinhSachBaoMatPage(Model model) {
		List<DiscountDTO> couponList = discountService.findAll();
		model.addAttribute("couponList", couponList);
		return "chinh-sach-bao-mat";
	}
	
	@RequestMapping("/bang-bao-gia-dat-banner-va-text-link")
	public String bangBaoGiaPage(Model model) {
		List<DiscountDTO> couponList = discountService.findAll();
		model.addAttribute("couponList", couponList);
		return "bang-bao-gia-dat-banner-va-text-link";
	}
	
	@RequestMapping("/lien-he")
	public String lienHePage(Model model) {
		List<DiscountDTO> couponList = discountService.findAll();
		model.addAttribute("couponList", couponList);
		return "lien-he";
	}
	
	@RequestMapping("/403-Page")
	public String accessDenial() {
		return "403-Page";
	}
}
