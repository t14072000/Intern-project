package com.group4.serviceImplement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group4.DTO.DiscountDTO;
import com.group4.entity.Discount;
import com.group4.repo.DiscountRepository;
import com.group4.service.DiscountService;

@Service
public class DiscountServiceImplement implements DiscountService{
	
	@Autowired
	private DiscountRepository disRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public List<DiscountDTO> findAll() {
		List <Discount> discountList = disRepo.findAll();
		List<DiscountDTO> discountDTOList = new ArrayList<DiscountDTO>();
		for (Iterator<Discount> iterator = discountList.iterator(); iterator.hasNext();) {
			Discount discount = (Discount) iterator.next();
			DiscountDTO discountDTO = new DiscountDTO(discount.getDiscountId(), discount.getDiscountName(), discount.getCode(),
					discount.getPercentage(), discount.getDiscountLinkPage(), discount.isStatus());
			discountDTOList.add(discountDTO);
		}
		return discountDTOList;
	}

	@Override
	public DiscountDTO findByDiscountName(String discountName) {
		return disRepo.findByDiscountName(discountName);
	}

	@Override
	public DiscountDTO findByCode(String code) {
		return disRepo.findByCode(code);
	}
	
	@Override
	public int getMaxDiscountID() {
		return disRepo.getMaxDiscountID();
	}
	
	@Override
	public boolean createDiscount(DiscountDTO discountDTO) {
		// TODO Auto-generated method stub
		try {
			Discount discount = mapper.map(discountDTO, Discount.class);
			disRepo.save(discount);
			return true;
		}
		catch(IllegalArgumentException ex) {
			return false;
		}
	}

	@Override
	public Integer getTotalDiscounts() {
		return disRepo.findAll().size();
	}

	@Override
	public List<DiscountDTO> findByStatus(boolean status) {
		return disRepo.findByStatus(status);
	}

	@Override
	public DiscountDTO findByDiscountId(Integer discountId) {
		// TODO Auto-generated method stub
		return disRepo.findByDiscountId(discountId);
	}

	

}
