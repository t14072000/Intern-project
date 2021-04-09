package com.group4.controller;

import java.sql.Timestamp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.group4.DTO.CourseDTO;
import com.group4.DTO.OrderDTO;
import com.group4.DTO.OrderDetailDTO;
import com.group4.service.CourseService;
import com.group4.service.OrderDetailService;
import com.group4.service.OrderService;

@Controller
public class OrderController {

	@Autowired
	OrderService orderService;
	
	@Autowired
	OrderDetailService orderDetailService;
	
	@Autowired
	CourseService courseService;
	
	@RequestMapping(value = "/order-course", method = RequestMethod.POST)
	@ResponseBody
	public String orderCourse(@RequestParam("txtName") String name, @RequestParam("txtEmail") String email,
			@RequestParam("txtPhone") String phone, @RequestParam("txtFacebook") String facebook, @RequestParam("txtAddress") String address,
			@RequestParam(value = "txtCourseID", required = true) String courseID, @RequestParam("payment_type") String paymentType,
			@RequestParam("txtTotal") String total) {	
		CourseDTO courseDTO = courseService.findByCourseId(Integer.parseInt(courseID));
		if(paymentType.equals("2")) {
			if(address.trim().length() == 0) {
				return "Address is empty";
			}
		}
		int orderID = orderService.getMaxOrderID() + 1;
		
		long millis = System.currentTimeMillis();
        Timestamp datetime = new Timestamp(millis);
        
		OrderDTO orderDTO = new OrderDTO(orderID, name, phone, email, facebook, datetime, address, total, "Processing", Integer.parseInt(paymentType));
		
		boolean result = orderService.createOrder(orderDTO);
		if(!result) {
        	return "FAIL";
        }
		
		int orderDetailID = orderDetailService.getMaxOrderDetailID() + 1;
		OrderDetailDTO orderDetailDTO = new OrderDetailDTO(orderDetailID, orderID, courseDTO.getCourseId());
		result = orderDetailService.createOrderDetail(orderDetailDTO);
		if(!result) {
        	return "FAIL";
        }
        
		return "SUCCESS";
	}
}
