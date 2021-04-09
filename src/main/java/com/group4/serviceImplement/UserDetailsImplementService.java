package com.group4.serviceImplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.group4.DTO.AdminDTO;
import com.group4.repo.AdminRepository;
import com.group4.userdetail.CustomUserDetails;

@Service
public class UserDetailsImplementService implements UserDetailsService{

	@Autowired
    private AdminRepository adminRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username){
		// TODO Auto-generated method stub
		AdminDTO adminDTO = null;
		try {
			adminDTO = adminRepository.findByUsername(username);
		}
		catch (UsernameNotFoundException e) {
			// TODO: handle exception
			return new CustomUserDetails(adminDTO);
		}
        return new CustomUserDetails(adminDTO);
	}

}
