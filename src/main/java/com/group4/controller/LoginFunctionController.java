package com.group4.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginFunctionController {

    @RequestMapping("/login")
    public String loginPage(Model model) {
        return "login";
    }

}
