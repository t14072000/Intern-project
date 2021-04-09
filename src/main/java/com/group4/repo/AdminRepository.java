package com.group4.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.group4.DTO.AdminDTO;
import com.group4.entity.Admin;

public interface AdminRepository extends CrudRepository<Admin, Integer>{
	List<Admin> findAll();
	
	AdminDTO findByUsername(String username);
}
