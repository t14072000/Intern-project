package com.group4.service;

import java.util.List;

import com.group4.DTO.OrderDetailDTO;

public interface OrderDetailService {
	
	int getMaxOrderDetailID();
	
	boolean createOrderDetail(OrderDetailDTO orderDetailDTO);
	
	List<Integer> getTop10OrdersIdByPostId(Integer productId);
}
