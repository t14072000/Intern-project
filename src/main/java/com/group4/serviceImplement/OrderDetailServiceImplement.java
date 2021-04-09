package com.group4.serviceImplement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group4.DTO.OrderDetailDTO;
import com.group4.entity.OrderDetail;
import com.group4.repo.OrderDetailRepository;
import com.group4.service.OrderDetailService;

@Service
public class OrderDetailServiceImplement implements OrderDetailService{
	
	@Autowired
	private OrderDetailRepository orderDetailRepo;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public boolean createOrderDetail(OrderDetailDTO orderDetailDTO) {
		// TODO Auto-generated method stub
		try {
			OrderDetail orderDetail = mapper.map(orderDetailDTO, OrderDetail.class);
			orderDetailRepo.save(orderDetail);
			return true;
		}
		catch(IllegalArgumentException ex) {
			return false;
		}
	}

	@Override
	public int getMaxOrderDetailID() {
		// TODO Auto-generated method stub
		return orderDetailRepo.getMaxOrderDetailID();
	}

	@Override
	public List<Integer> getTop10OrdersIdByPostId(Integer productId) {
		// TODO Auto-generated method stub
		List<OrderDetailDTO> orderDetailList = orderDetailRepo.findTop10ByProductIdOrderByOrderDetailIdDesc(productId);
		List<Integer> orderIdList = new ArrayList<>();
		for (Iterator<OrderDetailDTO> iterator = orderDetailList.iterator(); iterator.hasNext();) {
			OrderDetailDTO orderDetail = (OrderDetailDTO) iterator.next();
			orderIdList.add(orderDetail.getOrderId());
		}
		return orderIdList;
	}
}
