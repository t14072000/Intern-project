package com.group4.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.group4.DTO.DiscountDTO;
import com.group4.service.DiscountService;

@Controller
@RequestMapping(value = "/admin")
public class UpdateDiscountController {

	@Autowired
	private DiscountService discountService;
	
	@RequestMapping(value = "/search-discount")
	public String searchDiscountPage(Model model, Principal principal) {
		model.addAttribute("username", principal.getName());
		
		List <DiscountDTO> discountDTOList = discountService.findAll();
		model.addAttribute("discountDTOList", discountDTOList);
		return "search-discount-code";
	}
	
	@RequestMapping(value = "/update-discount-code/{discountID}")
	public String updateDiscountCodePage(Model model, @PathVariable("discountID") String discountID, Principal principal) {
		model.addAttribute("username", principal.getName());
		
		DiscountDTO discountDTO = null;
		
		try {
			discountDTO = discountService.findByDiscountId(Integer.parseInt(discountID));
		} catch(NumberFormatException ex) {
			return "redirect:/search-discount";
		}
		 
		if(discountDTO == null) {
			return "redirect:/search-discount";
		}
		model.addAttribute("discountDTO", discountDTO);
		
		return "update-discount-code";
	}
	
	@RequestMapping(value = "/update-discount-code", method = RequestMethod.POST)
	@ResponseBody
	public String updateDiscountCode(Model model, @RequestParam("txtDiscountID") String discountID, @RequestParam("txtDiscountName") String discountName,
			@RequestParam("txtDiscountCode") String discountCode, @RequestParam("txtPercentage") String percentage,
			@RequestParam("txtDiscountLink") String discountLink, @RequestParam(value = "Status", required = false) String status) {
		boolean discountStatus = false;
		if(status != null) {
			discountStatus = true;
		}
		
		DiscountDTO discountDTO = new DiscountDTO(Integer.parseInt(discountID), discountName, discountCode, Integer.parseInt(percentage),
				discountLink, discountStatus);
		
		boolean result = discountService.createDiscount(discountDTO);
		if (!result) {
			return "FAIL";
		}
		return "SUCCESS";
	}
}
