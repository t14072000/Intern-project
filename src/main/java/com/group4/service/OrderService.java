package com.group4.service;

import java.util.List;

import com.group4.DTO.OrderDTO;

public interface OrderService {

	boolean createOrder(OrderDTO orderDTO);
	
	int getMaxOrderID();
	
	List<OrderDTO> findByOrderIdIn(List<Integer> orderId);
}
