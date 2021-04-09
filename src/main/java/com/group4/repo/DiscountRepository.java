package com.group4.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.group4.DTO.DiscountDTO;
import com.group4.entity.Discount;

public interface DiscountRepository extends CrudRepository<Discount, Integer>{
	List<Discount> findAll();
	
	List<DiscountDTO> findByStatus(boolean status);
	
	DiscountDTO findByDiscountName(String discountName);
	
	DiscountDTO findByCode(String code);
	
	@Query(value = "SELECT coalesce(max(d.discount_id), 0) FROM discount d", nativeQuery = true)
	int getMaxDiscountID();
	
	DiscountDTO findByDiscountId(Integer discountId);
}
