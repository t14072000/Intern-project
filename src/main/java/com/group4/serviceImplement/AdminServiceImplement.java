package com.group4.serviceImplement;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group4.DTO.AdminDTO;
import com.group4.entity.Admin;
import com.group4.repo.AdminRepository;
import com.group4.service.AdminService;

@Service
public class AdminServiceImplement implements AdminService{

	@Autowired
	private AdminRepository adminRepository ;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public Integer getTotalAdmins() {
		return adminRepository.findAll().size();
	}

	@Override
	public boolean createAdmin(AdminDTO adminDTO) {
		// TODO Auto-generated method stub
		try {
			Admin admin = mapper.map(adminDTO, Admin.class);
			adminRepository.save(admin);
			return true;
		}
		catch(IllegalArgumentException ex) {
			return false;
		}
	}

	@Override
	public boolean isUsernameExisted(String username) {
		// TODO Auto-generated method stub
		return adminRepository.findByUsername(username) != null;
	}

}
