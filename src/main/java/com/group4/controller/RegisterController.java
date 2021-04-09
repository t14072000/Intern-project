package com.group4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.group4.DTO.AdminDTO;
import com.group4.service.AdminService;

@Controller
@RequestMapping(value = "/admin")
public class RegisterController {
	
	@Autowired
	private AdminService adminService;
	
	@RequestMapping(value = "/register")
    public String viewRegister(Model model) {
        AdminDTO admin = new AdminDTO(null, null);
        model.addAttribute("admin", admin);
        return "register";
    }
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
    public String register(@ModelAttribute("admin") AdminDTO admin) {
		boolean result = adminService.isUsernameExisted(admin.getUsername());
		if(result) {
        	return "User existed";
        }
        result = adminService.createAdmin(admin);
        if(!result) {
        	return "FAIL";
        }
        return "SUCCESS";
    }
}
