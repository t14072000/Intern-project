package com.group4.serviceImplement;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group4.DTO.OrderDTO;
import com.group4.entity.Order;
import com.group4.repo.OrderRepository;
import com.group4.service.OrderService;

@Service
public class OrderServiceImplement implements OrderService{

	@Autowired
	private OrderRepository orderRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public boolean createOrder(OrderDTO orderDTO) {
		// TODO Auto-generated method stub
		try {
			Order order = mapper.map(orderDTO, Order.class);
			orderRepo.save(order);
			return true;
		}
		catch(IllegalArgumentException ex) {
			return false;
		}
	}

	@Override
	public int getMaxOrderID() {
		// TODO Auto-generated method stub
		return orderRepo.getMaxOrderID();
	}

	@Override
	public List<OrderDTO> findByOrderIdIn(List<Integer> orderId) {
		// TODO Auto-generated method stub
		return orderRepo.findByOrderIdIn(orderId);
	}

}
