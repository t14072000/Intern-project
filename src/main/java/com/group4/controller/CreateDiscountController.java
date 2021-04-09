package com.group4.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.group4.DTO.DiscountDTO;
import com.group4.service.DiscountService;

@Controller
@RequestMapping(value = "/admin")
public class CreateDiscountController {
	
	@Autowired
	private DiscountService discountService;
	
	@RequestMapping(value = "/create-discount-code")
	public String createDiscountCodePage(Model model, Principal principal) {
		model.addAttribute("username", principal.getName());
		return "create-discount-code";
	}
	
	@RequestMapping(value = "/create-discount-code", method = RequestMethod.POST)
	@ResponseBody
	public String createDiscountCode(@RequestParam("txtDiscountName") String discountName,
			@RequestParam("txtDiscountCode") String discountCode, @RequestParam("txtPercentage") String percentage,
			@RequestParam("txtDiscountLink") String discountLink) {	
		if(discountService.findByCode(discountCode) != null) {
			return "Discount code already existed";
		}
		Integer discountID = discountService.getMaxDiscountID() + 1;
		DiscountDTO discountDTO = new DiscountDTO(discountID, discountName, discountCode, Integer.parseInt(percentage), discountLink, true);
		boolean result = discountService.createDiscount(discountDTO);
		if(!result) {
			return "FAIL";
		}
		return "SUCCESS";
	}
}
