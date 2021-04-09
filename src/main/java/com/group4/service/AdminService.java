package com.group4.service;

import com.group4.DTO.AdminDTO;

public interface AdminService {
	
	Integer getTotalAdmins();
	
	boolean createAdmin(AdminDTO adminDTO);
	
	boolean isUsernameExisted(String username);
}
