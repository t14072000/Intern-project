package com.group4.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "admin")
public class Admin {
	@Id
	@Column(name = "username", nullable = false)
	private String username;
	
	@Column(name = "password")
	private String password;
	
	private PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = passwordEncoder().encode(password);
	}

}
