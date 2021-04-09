package com.group4.userdetail;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.group4.DTO.AdminDTO;

import lombok.Data;

@Data
public class CustomUserDetails implements UserDetails{
	
	AdminDTO adminDTO;
	
	public CustomUserDetails(AdminDTO adminDTO) {
		// TODO Auto-generated constructor stub
		this.adminDTO = adminDTO;
	}

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public String getPassword() {
        return adminDTO.getPassword();
    }

    @Override
    public String getUsername() {
        return adminDTO.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
