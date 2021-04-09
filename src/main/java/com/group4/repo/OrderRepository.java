package com.group4.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.group4.DTO.OrderDTO;
import com.group4.entity.Order;

public interface OrderRepository extends CrudRepository<Order, Integer>{

	@Query(value = "SELECT coalesce(max(o.order_id), 0) FROM [dbo].[order] o", nativeQuery = true)
	int getMaxOrderID();
	
	List<OrderDTO> findByOrderIdIn(List<Integer> orderId);
}
