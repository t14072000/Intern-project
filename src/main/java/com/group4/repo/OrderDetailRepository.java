package com.group4.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.group4.DTO.OrderDetailDTO;
import com.group4.entity.OrderDetail;

public interface OrderDetailRepository extends CrudRepository<OrderDetail, Integer>{

	@Query(value = "SELECT coalesce(max(o.order_detail_id), 0) FROM order_detail o", nativeQuery = true)
	int getMaxOrderDetailID();
	
	List<OrderDetailDTO> findTop10ByProductIdOrderByOrderDetailIdDesc(Integer productId);
}
